package com.jt.test.demo1.controller;

import com.jt.test.demo1.common.HttpResult;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

/**
 * FileController
 *
 * @author jt
 * @date 2022/5/9
 **/
@Api
@Controller
@RequestMapping("/file")
public class FileController {
    public static  int fileCount = 0;
    public static  int directoryCount = 0;
    public static  int isDirectory = 0;

//    public static void main(String[] args) {
//        System.out.println("输入文件路径");
//        Scanner sc = new Scanner(System.in);
//        //如果文件路径名有空格会发生异常，需要nextline去获取输入内容
//        String pathName = sc.nextLine();
//        System.out.println(pathName);
//        File file = new File(pathName);
//        System.out.println("当前路径是否存在文件"+file.exists());
//        System.out.printf("文件名称"+file.getName());
//        System.out.println("文件长度"+file.length());
//        System.out.println("最后更新时间"+file.lastModified());
//        System.out.println("文件绝对路径是"+file.getAbsolutePath());
//        System.out.println("==============判断完成=============");
//        new FileController().getFiles(file);
//        System.out.println("==============循环遍历当前目录所有文件（包括子目录下的）=============");
//
//    }
    @ResponseBody
    @PostMapping("/get")
    public HttpResult getFiles(File file){
        //得到所有文件
        File[] files = file.listFiles();
        if("0".equals(String.valueOf(isDirectory))){
        System.out.printf("文件夹名称："+file.getName());
        System.out.println("当前路径是否存在文件："+file.exists());
        System.out.println(String.format("文件夹总长度：" + file.length(),"yyyy-MM-dd"));
        System.out.println("最后更新时间："+file.lastModified());
        System.out.println("文件夹绝对路径是："+file.getAbsolutePath());
        isDirectory++;
        }
        if(null != files){
            for (int i = 0;i<files.length;i++){
                String result = files[i].isFile()? "一个文件": "一个目录";
                System.out.println(files[i]+"\t"+result);
                if ("一个目录".equals(result)){
                    if("1".equals(String.valueOf(isDirectory)))
                    {FileController.isDirectory--;}
                    getFiles(files[i]);
                    FileController.directoryCount++;
                }else{
                    FileController.fileCount++;
                }
            }

            if ("1".equals(String.valueOf(isDirectory))){
                isDirectory--;
            }
            return HttpResult.success();
        }else
        {
            return HttpResult.failed("错误：文件夹不存在或文件夹内没有文件");
        }
    }

}
