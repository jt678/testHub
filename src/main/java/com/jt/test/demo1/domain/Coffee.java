package com.jt.test.demo1.domain;

/**
 * Coffee
 * 咖啡抽象类
 *
 * @Author: jt
 * @Date: 2023/9/4 15:50
 */
public abstract class Coffee {
    public abstract String getName();

    public void addSuger() {
        System.out.println("咖啡--加糖方法");
    }

    public void addMilk() {
        System.out.println("咖啡--加奶方法");
    }

}
