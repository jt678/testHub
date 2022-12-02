package com.jt.test.demo1.junitTest;

import com.jt.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * StringTest
 *
 * @author jt
 * @date 2022/5/6
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
/**
 * 使用场景：
 * String基本测试，
 * String（+） 字符不经常变化，如常量声明，少量变量运算
 * StringBuilder（.append()）动态修改，单线程，如sql拼装，Json封装
 * StringBuffer 动态修改，多线程安全环境如Xml解析，http参数解析，封装
 */
public class StringTest {
    /**
     * SB测试
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

    /**
     * String测试
     */
    @Test
    public void  String(){
        String a = "这是一个字符串";
        a = a + "!!!";
        String baseStr = a.concat("concat");
        System.out.println(baseStr);

        String str = "这是另外一个字符串";
        //delimiter:每个元素要添加的分隔符，elements：需要添加的元素
        str = String.join("---",str,baseStr,"这是join测试");
        if (!str.matches("[a-b]{2}")) {
            System.out.println(str);
            return;
        }
    }

    /**
     * Array和List的测试
     * 实例化;substring;大小写转换;Array;List转换,Arrays方法
     */
    @Test
    public void StringArrayAndList(){
        String testString = "this is a test";
        String[] tests = testString.split(" ");
        String[] arrayStr = new String[]{"Java","Test","Array","List"};
//        String b = testString.substring(3,4);

        //Int显式转换成String
        int[] array = new int[]{1,2,3,4};
        String string1 = Integer.toString(1);

        for (String test :tests) {
            System.out.println(test);
        }
        System.out.println(Arrays.toString(tests));
        System.out.println(Arrays.toString(arrayStr));

        //转成首字母大写测试
        String upperCase = testString.substring(0, 1).toUpperCase();
        String after = testString.substring(1);
        testString = upperCase + after;
        System.out.println(testString);

        //String转String数组里面的每个字符串都转成大写测试
        List<String> testList= new ArrayList<>();
        for (String test:tests) {
            String upTestString = test.substring(0, 1).toUpperCase()+test.substring(1) ;
            testList.add(upTestString);
        }
        String[] testArray = testList.toArray(new String[4]);
        System.out.println(Arrays.toString(testArray));
    }

}
