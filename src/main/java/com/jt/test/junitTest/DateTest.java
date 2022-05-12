package com.jt.test.junitTest;

import com.jt.test.TestApplicationMapTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;

/**
 * DateTest
 *
 * @author jt
 * @date 2022/5/12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationMapTest.class)
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
}
