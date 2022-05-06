package com.jt.test.junitTest;

import com.jt.test.TestApplicationMapTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * StringTest
 *
 * @author jt
 * @date 2022/5/6
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplicationMapTest.class)
public class StringTest {
    /**
     * 使用场景：
     * String基本测试，
     * String（+） 字符不经常变化，如常量声明，少量变量运算
     * StringBuilder（.append()）动态修改，单线程，如sql拼装，Json封装
     * StringBuffer 动态修改，多线程安全环境如Xml解析，http参数解析，封装
     */
    @Test
    public void StringBuilderTest(){

        //append()
        StringBuilder stringBuilder = new StringBuilder("这是一个初始的StringBuilder");
        System.out.println(stringBuilder);
        stringBuilder.append("，append加的");
        //replace()
        stringBuilder.replace(0,2,"替换");
        System.out.println(stringBuilder);
        //delete(),deleteCharAt(index)
        stringBuilder.delete(20,20+9);
        System.out.println(stringBuilder);
        //insert(),后面接的insert(index,value)下标是当前的下标
        StringBuilder sb = new StringBuilder("sb测试");
        sb.insert(2,"的").insert(5,"!!");
        System.out.println(sb);
        //reverse():将字符串倒序
        StringBuilder sb2 = new StringBuilder("sb2测试");
        System.out.println(sb2.reverse());
    }

    @Test
    public void  String(){
        String a = "这是一个字符串";
        a = a + "!!!";
        String baseStr = a.concat("concat");
        System.out.println(baseStr);

        String str = "这是另外一个字符串";
        System.out.println(str);
        //delimiter:每个元素要添加的分隔符，elements：需要添加的元素
        str = String.join("---",str,baseStr,"这是join测试");
        System.out.println(str);
    }
}
