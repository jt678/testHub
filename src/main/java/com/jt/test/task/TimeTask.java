package com.jt.test.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.test.domain.Company;
import com.jt.test.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TimeTask
 *
 * @Author: jt
 * @Date: 2022/6/27 10:36
 */
@Slf4j
@Component
@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class TimeTask {
    @Autowired
    private CompanyService companyService;


    //每分钟的第*0秒开始，10秒钟一次
//    {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
    @Scheduled(cron = "*/10 40 * * * *")
    @Async
    public void sync2() {
        try {
            //得到4分钟前的时间

            LocalDateTime fourMinBefore = LocalDateTime.now().minusMinutes(1);
            //查数据库里面在一分钟之前的数据
            List<Company> list = companyService.list(new LambdaQueryWrapper<Company>().le(Company::getTime, fourMinBefore));
            if(list != null && !list.isEmpty()){
                System.out.println("这些是一分钟前的数据："+JSONObject.toJSONString(list));
            }else System.out.printf("没有一分钟前的数据");
        }catch (Exception e){

            log.warn("定时任务失败");
            e.printStackTrace();
        }
    }
}
