package com.jt.test.demo1.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import sun.net.ftp.FtpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * FtpUtil
 *
 * @Author: jt
 * @Date: 2023/4/19 11:02
 */
@Slf4j
@Component
public class FtpUtil {
    @Value("${ftp.host}")
    private String ftpHost;
    @Value("${ftp.port}")
    private String ftpPort;
    @Value("${ftp.userName}")
    private String ftpUserName;
    @Value("${ftp.passWord}")
    private String ftpPassword;

//    @Value("ftp.host")
//    public static void setFtpHost(String ftpHost) {
//        FtpUtil.ftpHost = ftpHost;
//    }
//    @Value("ftp.port")
//    public static void setFtpPort(String ftpPort) {
//        FtpUtil.ftpPort = ftpPort;
//    }
//    @Value("ftp.userName")
//    public static void setFtpUserName(String ftpUserName) {
//        FtpUtil.ftpUserName = ftpUserName;
//    }
//    @Value("ftp.passWord")
//    public static void setFtpPassword(String ftpPassword) {
//        FtpUtil.ftpPassword = ftpPassword;
//    }

    /**
     * 初始化ftp客户端
     *
     * @return
     */
    public FTPClient initFtpClient() {
        //注意这里使用的是apache的，不是sun的client
        FTPClient ftpClient = new FTPClient();
        try {
            // 连接FPT服务器,设置IP及端口
            ftpClient.connect(ftpHost, Integer.parseInt(ftpPort));

            // 设置用户名和密码
            ftpClient.login(ftpUserName, ftpPassword);

            // 设置连接超时时间,5000毫秒
            ftpClient.setConnectTimeout(50000);

            // 设置中文编码集，防止中文乱码
            ftpClient.setControlEncoding("UTF-8");
            // 连接返回状态码
            int replyCode = ftpClient.getReplyCode();
            System.out.println("连接状态码:" + replyCode);
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.info("FTP 连接失败,ftp服务器:" + ftpHost + ":" + ftpPort);
                ftpClient.disconnect();
            } else {
                log.info("FTP 连接成功,ftp服务器:" + ftpHost + ":" + ftpPort);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return ftpClient;
    }
    /**
     * 删除文件
     */
    public Boolean deleteFile(FTPClient ftpClient,String fileName,Boolean flag){
        System.out.println("开始删除文件");
        try {
            boolean isDelete = ftpClient.deleteFile(fileName);
            if (isDelete) {
                System.out.println("文件删除成功");
                flag = true;
            } else {
                System.out.println("删除失败！");
            }
            ftpClient.logout();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return flag;
    }

    /**
     * 从ftp服务器上下载文件到指定目录
     *
     * @param ftpPath      FTP服务器上文件路径（相对/绝对都行）
     * @param downloadPath 下载保存的位置
     * @return
     */
    public boolean downloadFile(String ftpPath, String downloadPath, OutputStream out) {
        boolean flag = false;
        System.out.println("开始下载文件");
        FTPClient ftpClient = this.initFtpClient();
        try {
            //切换到工作目录
            ftpClient.changeWorkingDirectory(ftpPath);
            //拿到该目录下所有文件
            FTPFile[] ftpFiles = ftpClient.listFiles();
            System.out.println("读取到FTP文件大小===>" + ftpFiles.length);
            //逐条去下载
            for (FTPFile ftpFile : ftpFiles) {
                String fileName = ftpFile.getName();
                System.out.println("当前处理文件===>" + fileName);
                //处理zip文件，此处还可以定义其他文件的处理
                if (fileName.contains("zip")) {
                    //在本地write这个文件
                    File localFile = new File(downloadPath +"/"+ fileName);
                    //创建一个out子类，让out去引用他
                    out = new FileOutputStream(localFile);
                    //从服务器检索名为fileName文件，并将其写入给定的os
                    boolean mark = ftpClient.retrieveFile(fileName, out);
                    out.close();
                    //写完之后关闭out流并删除ftp上的文件
                    if (mark) {
                        flag = this.deleteFile(ftpClient, fileName, flag);
                    }
                } else if (fileName.contains("txt")) {
                    //todo:do something about txt
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    log.error(ioe.getMessage());
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
