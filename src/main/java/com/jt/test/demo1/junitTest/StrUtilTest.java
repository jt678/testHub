package com.jt.test.demo1.junitTest;

import cn.hutool.core.util.StrUtil;
import com.jt.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * StrUtilTest
 *
 * @author jt
 * @date 2022/5/9
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class StrUtilTest {
    @Test
    public void StrJudgeTest(){
        //isEmpty
        System.out.println("isEmpty:");
        System.out.println(StrUtil.isEmpty(null)); 
        System.out.println(StrUtil.isEmpty(""));
        System.out.println(StrUtil.isEmpty(" ")); 
        System.out.println(StrUtil.isEmpty("aaa"));
        System.out.println(StrUtil.isEmpty("\r \t \n \f")+"\n");
        //isBlank
        System.out.println("isBlank:");
        System.out.println(StrUtil.isBlank(null));
        System.out.println(StrUtil.isBlank("")); 
        System.out.println(StrUtil.isBlank(" ")); 
        System.out.println(StrUtil.isBlank("aaa"));
        System.out.println(StrUtil.isBlank("\r \n \t \f")+"\n");
        //hasEmpty
        System.out.println("hasEmpty:");
        System.out.println(StrUtil.hasEmpty(null,""));
        System.out.println(StrUtil.hasEmpty(""));
        System.out.println(StrUtil.hasEmpty(" "));
        System.out.println(StrUtil.hasEmpty("aaa"," "));
        System.out.println(StrUtil.hasEmpty("\r \n \t \f")+"\n");
        //hasBlank
        System.out.println("hasBlank:");
        System.out.println(StrUtil.hasBlank(null,"not blank"));
        System.out.println(StrUtil.hasBlank(""));
        System.out.println(StrUtil.hasBlank(" "));
        System.out.println(StrUtil.hasBlank("aaa"," ","bb"));
        System.out.println(StrUtil.hasBlank("\r \n \t \f")+"\n");
    }

    @Test
    public void StrOperation(){
        String testStr = "a这是一个测试用例b";
        String str1 = StrUtil.removeSuffix(testStr, "用例b");
        String str2 = StrUtil.removeSuffix(testStr, "用例B");
        String str3 = StrUtil.removeSuffixIgnoreCase(testStr, "用例B");
        String str4 = StrUtil.addSuffixIfNot(testStr, "用例a");

        //同理prefix也有三个
//        StrUtil.removePrefix()
    }

    @Test
    public void StrFormat(){
        String stringFormat = "{}是{},{}";
        String prefix = "random";
        String suffix = "随机的";

        System.out.println(StrUtil.format(stringFormat, prefix, suffix,"那么这个呢?"));
    }
}
