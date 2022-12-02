package com.jt.test.demo1.junitTest.java8Test;

import com.jt.test.TestApplication;
import com.jt.test.demo1.common.*;
import com.jt.test.demo1.service.BrandService;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;


/**
 * Java8Test
 *
 * @Author: jt
 * @Date: 2022/6/28 14:21
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class Java8LambdaTest {
    @Autowired
    private BrandService brandService;

    /**
     * lambda定义接口
     */
    @Test
    public void lambdaInterface(){
        /**
         * 无返回，无参
         */
        NoReturnNoParam noReturnNoParam = ()->{
            System.out.println("NoReturnMultiParam");
        };
//        NoReturnNoParam noReturnNoParam1 = ()-> System.out.println("简化版本");
        noReturnNoParam.method();

        /**
         * 无返回，有一参
         */
        NoReturnOneParam noReturnOneParam = (int a)-> System.out.println("NoReturnOneParam"+a);
        noReturnOneParam.method(1);

        /**
         * 无返回，多参
         */
        NoReturnMultiParam noReturnMultiParam = (int a,int b, String c)-> System.out.println("NoReturnMultiParam"+c+(a+b));
        noReturnMultiParam.method(1,1,"的结果是：");

        /**
         * 有返回，无参
         */
        ReturnNoParam returnNoParam = ()-> 1+2+3;

        System.out.println(returnNoParam.method());

        /**
         * 有返回，一参
         */
        ReturnOneParam returnOneParam = a ->1+2+3+a;
        System.out.println(returnOneParam.method(4));

        /**
         * 有返回，多参
         */
        ReturnMultiParam returnMultiParam = (a, b) -> 1+2+3+a+b;
        System.out.println(returnMultiParam.method(4, 5));
    }

    /**
     * lambda表达式的引用->方法
     * @param a
     * @return
     */
//  要求：返回值类型,参数个数和类型 需要和函数接口的抽象方法一致
    public static int doubleNum(int a){
        return 2*a;
    }

    public int addTwo(int a){
        return a+2;
    }

    @Test
    public void lambdaReference(){
        ReturnOneParam returnOneParam = Java8LambdaTest::doubleNum;
        //相当于用doubleNum和addTwo实现了ReturnOneParam接口
        int method = returnOneParam.method(10);
        System.out.println(method);

        Java8LambdaTest java8Test = new Java8LambdaTest();
        ReturnOneParam returnOneParam1 = java8Test::addTwo;
        int method1 = returnOneParam1.method(10);
        System.out.println(method1);
    }

    /**
     * lambda表达式创建线程
     */
    public void lambdaThread(){
        System.out.println(Thread.currentThread().getName()+" 开始");
        new Thread(()->{
            for(int i=0;i<20;i++){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" "+i);
            }
        },"Lambda Thread ").start();
        System.out.println(Thread.currentThread().getName()+" 结束");

    }

    /**
     * 操作集合：1.遍历集合
     *         2.删除
     *         3.排序
     */
    @Test
    public void lambdaCollection(){
//        List<Brand> brandList = brandService.list(new LambdaQueryWrapper<Brand>().select(Brand::getName,Brand::getLogo));
        ArrayList<String> list = Lists.newArrayList();
        list.add(0,"a");
        list.add(1,"b");
        list.add(2,"c");
        list.add(3,"d");
        //逐条打印
        list.forEach(System.out::println);
        //删除集合中的元素
        list.removeIf(element -> element.equals("b"));

        list.forEach(System.out::println);
        //集合排序，也可以用Comparator.comparing(Brand::getProductCount),在java8新特性流的实验中有用到过
//       brandList.sort(((o1, o2) -> o1.getProductCount().compareTo(o2.getProductCount())));
    }
}
