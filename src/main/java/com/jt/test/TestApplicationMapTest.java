package com.jt.test;

import com.jt.test.helper.OrderHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.*;

/**
 * @author j
 * @date 2022/3/8
 */
//使用这个只能使用swagger，不能解决knife4j的问题
//@EnableWebMvc
@SpringBootApplication
@MapperScan("com.jt.test.mapper")
public class TestApplicationMapTest {
    private static OrderHelper helper;
    private static Object OrderBO;

    public static void main(String[] args) {
        Date now = new Date();
        SpringApplication.run(TestApplicationMapTest.class, args);


//            System.out.println("测试项目启动成功~!hhh"+now);

        Map<String,Integer> testMap = new HashMap<>();

//        testMap.put("a",1);
//        testMap.put("b",2);
//        testMap.put("c",3);
//        testMap.put("d",4);
//        System.out.println("map初始化的值"+":"+testMap);
//
//        testMap.put("d",5);
//
//        testMap.put("f",10);
//        System.out.println("d put 5,put f(之前map不存在) 10"+":"+testMap);
//
//        testMap.replace("g",10);
//        System.out.println("g(不存在) replace 10"+":"+testMap);

        //构造map
        testMap.put("assz2",1132132145);
        testMap.put("bxz12",2321355);
        testMap.put("xzaaw2c",3321567);
        testMap.put("ddsaz23",432131);
        testMap.put("sadz21",213213);
        testMap.put("sadaz2231",32144519);
        testMap.put("zxpi12",30009321);
        testMap.put("samkoo12",2131245);

        //String的hashCode检测，发现string重写了hashcode是根据字符数组的内容来确定hashcode，内容一样hashcode也一样，但是用==比较内存地址会不同，返回false
        String i = "h312213312aaaa";
        int i1 = i.hashCode();

        String j = "h312213312aaaa";
        int i2 = j.hashCode();

//        三种视图
        Set<Map.Entry<String, Integer>> entrySet = testMap.entrySet();
        Set<String> keySet = testMap.keySet();
        Collection<Integer> values = testMap.values();

//        迭代器
        Iterator<Map.Entry<String, Integer>> entryIterator = entrySet.iterator();
        Iterator<String> keyIterator = keySet.iterator();
        Iterator<Integer> valuesIterator = values.iterator();

        while (entryIterator.hasNext()){
            Map.Entry<String, Integer> mapEntry = entryIterator.next();
            System.out.println("K: "+mapEntry.getKey()+" "+"V:"+mapEntry.getValue());
        }
        testMap.clear();
        System.out.println("===========================================启动成功===========================================");

    }


}
