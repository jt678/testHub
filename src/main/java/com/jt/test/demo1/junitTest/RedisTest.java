package com.jt.test.demo1.junitTest;

import com.jt.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Redis,Array,List测试
 *
 * @author jt
 * @date 2022/5/5
 **/
@RunWith(SpringJUnit4ClassRunner.class )
@SpringBootTest(classes = TestApplication.class)
public class RedisTest {

    @Autowired
    private RedisConnectionFactory factory;

    @Test
    public void testRedis(){
        //得到一个redis连接
        RedisConnection connection = factory.getConnection();
        connection.set("hello2".toLowerCase().getBytes(),"redis".toUpperCase().getBytes());
//        System.out.println(new String(connection.get("hello".getBytes())));

        String testString = "this is a test";
        String[] tests = testString.split(" ");
        String[] arrayStr = new String[]{"Java","Test","Array","List"};
//        String b = testString.substring(3,4);

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
