//package com.jt.test.demo1.task;
//
//import com.jt.test.demo1.utils.FtpUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * DownloadTask
// *
// * @Author: jt
// * @Date: 2023/4/19 16:51
// */
//@Slf4j
//@Component
//@Configuration
//@RequiredArgsConstructor
//@EnableScheduling
//public class DownloadTask {
//    @Value("${ftp.filePath}")
//    private String filePath;
//    @Value("${ftp.downloadPath}")
//    private String downloadPath;
//    @Autowired
//    private FtpUtil ftpUtil;
//
//
//    @Scheduled(cron = "*/30 * * * * *")
//    @Async
//    public void ftpUtils(){
//        ftpUtil.downloadFile(filePath,downloadPath, null);
//    }
//}
