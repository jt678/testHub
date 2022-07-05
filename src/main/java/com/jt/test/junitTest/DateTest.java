package com.jt.test.junitTest;

import com.jt.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * DateTest
 *
 * @author jt
 * @date 2022/5/12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class DateTest {
    @Test
    public void dateTest() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String formatDate = simpleDateFormat.format(date);

        LocalDateTime nowTime = LocalDateTime.now();
        LocalDate now = LocalDate.now();


        Date parse = simpleDateFormat.parse("2022-05-01");
        String formatParse = simpleDateFormat.format(parse);

        System.out.println("new Date:"+formatDate+"\n"+"LocalDateTime:"+nowTime+"\n"+"LocalDate:"+now+"\n"+"解析时间："+formatParse);
    }

    /**
     * 计算时间---这种比较复杂，可以取LocalDateTime和相关函数计算
     * @throws ParseException
     */
    @Test
    public void dateMath() throws ParseException {
        String dateStart = "2013-02-19 09:30:58";

        String dateStop = "2013-02-20 11:35:48";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;

        Date d2 = null;


        d1 = format.parse(dateStart);

        d2 = format.parse(dateStop);

        long diff = d2.getTime() - d1.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;

       if(diffMinutes >= 4){
           System.out.println("超过五分钟");
       }
    }





}
