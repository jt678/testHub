package com.jt.test;

import com.jt.test.helper.OrderHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

/**
 * @author j
 * @date 2022/3/8
 */
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
        testMap.put("a",1);
        testMap.put("b",2);
        testMap.put("c",3);
        testMap.put("d",4);
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
        System.out.println("hahaha这是测试更新");

    }


}
