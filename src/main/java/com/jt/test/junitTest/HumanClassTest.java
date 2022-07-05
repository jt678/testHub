package com.jt.test.junitTest;

import com.jt.test.TestApplication;

import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassTest
 *
 * @author jt
 * @date 2022/5/13
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class HumanClassTest {
    //定义了基本方法，和公共变量人眼数量以及牙齿数量，都是private
    private static final int eyes = 2;

    private static void count() {
        System.out.println("I can count number");
    }

    private int teeth = 10;

    private void say() {
        System.out.println("Hello world");
    }

    /**
     * 定义一个内部类
     */
    class InsideClass{

        private String sexuality;
        private Integer age;

        public InsideClass(String sexuality, Integer age) {
            this.sexuality = sexuality;
            this.age = age;
        }
        //尝试调用外部类的private访问权限的实例变量和类变量

        public void tesst(){
            say();
            count();
            System.out.println(eyes);
            System.out.println(teeth);
            System.out.println(sexuality);
            System.out.println(age);

        }
    }

    /**
     *  定义一个静态内部类
     */
    static class InsideStaticClass{
        private String toes;

        public InsideStaticClass(String toes) {
            this.toes = toes;
        }

        public void test2(){
            //say必须要创建humanClassTest实例去调用方法，不能直接调用，teeth同理
            HumanClassTest humanClassTest = new HumanClassTest();
            humanClassTest.say();
            //cout()和eyes是static公共的所以可以直接调用
            count();
            System.out.println(eyes);
            System.out.println(humanClassTest.teeth);
            System.out.println(toes);
        }
    }


    public static void main(String[] args) {
        //非静态使用父类new一个实例再调用其内部方法
        HumanClassTest humanClassTest = new HumanClassTest();
        InsideClass insideClass = humanClassTest.new InsideClass("male", 15);
        insideClass.tesst();

        //静态可直接创建实例调用方法
        InsideStaticClass insideStaticClass = new InsideStaticClass("11");
        insideStaticClass.test2();
    }

    public static  final int max_test  = 1;




}
