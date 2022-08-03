package com.jt.test.junitTest;

import com.jt.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * regularExpressionTest
 *
 * @Author: jt
 * @Date: 2022/7/11 13:46
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class regularExpressionTest {
    /**
     * 正则表达式判断是否为手机号
     */
    @Test
    public void isChinaMobile(){
        String  testMobile = "" ;


        /**
         * "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9                            中的任意一位,
         * [^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
         */
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(testMobile);
        boolean result = m.matches();
        System.out.println(result);

    }
}
