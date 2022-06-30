package com.jt.test.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.test.domain.Company;
import com.jt.test.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.Local;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    @Scheduled(cron = "0 */5 * * * *")
//    @Scheduled(cron = "*/10 * * * * *")
    @Async
    public void sync() {
        try {
            //得到4分钟前的时间

            LocalDateTime fourMinBefore = LocalDateTime.now().minusMinutes(4);
            //查数据库里面在一分钟之前的数据
            ArrayList<Company> companyList = Lists.newArrayList();
            List<Company> list = companyService.list(new LambdaQueryWrapper<Company>().le(Company::getTime, fourMinBefore)
                                                        .eq(Company::getStatus,1));
            //查出来有四分钟之前的数据且status=1
            if(list != null && !list.isEmpty()){
                for (Company company : list) {

                    company.setStatus(0);
                    company.setTime(new Date());
                    companyList.add(company);
                }

                companyService.saveOrUpdateBatch(companyList);
                List<Company> returnList = companyService.list(new LambdaQueryWrapper<Company>().eq(Company::getStatus, 0));
                System.out.println("这些是四分钟前在线未接收到信号的数据："+JSONObject.toJSONString(returnList));
            }else

            System.out.printf("没有四分钟前的数据");
        }catch (Exception e){

            log.warn("定时任务失败");
            e.printStackTrace();
        }
    }
    @Async
//    @Scheduled(cron = "*/10 * * * * *")
    public void sync2(){
        try {
            //这种算会导致日期不同，但是时间没有相差到1分钟，就会判断成为分钟差为0，实际上已经差了x天了，而且如果小时不同，只会显示相差一分钟
            String dateStart = "2013-02-19 08:08:02";

            String dateStop = "2013-02-20 09:10:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateStart1 = sdf.parse(dateStart);
            Date dateStop1 = sdf.parse(dateStop);

            long diff = dateStop1.getTime()- dateStart1.getTime();
            long minutes = diff / (60 * 1000) % 60;
            System.out.println("相差时间："+diff+"相差分钟："+minutes);
        }catch (Exception e){

            log.warn("定时任务2失败");
        }
    }
}
