package com.jt.test.demo1.common;

/**
 * SingletonStaticInner
 * 静态内部类
 * @Author: jt
 * @Date: 2022/12/9 14:12
 */
public class SingletonStaticInner {
    private SingletonStaticInner(){

    }
    //在静态内部类里new一个实例
    private static class SingletonInner {
        private static SingletonStaticInner singletonStaticInner = new SingletonStaticInner();
    }

    public static SingletonStaticInner getInstance(){
        //直接获取类里面的属性(静态成员)
        return SingletonInner.singletonStaticInner;
    }
    //问题1：它是如何保证线程安全的；问题2：他和饿汉模式有什么区别
    //1:和饿汉模式相同的是它们都是靠JVM保证类的静态成员只能被加载一次的特点，这样从JVM层面实现了单例
    //2:饿汉模式是在开始启动程序时立即加载，而这种方式不是。原理是在加载一个类时，其中的内部类不会被加载，一个内部类被加载，当且仅当某个静态成员（静态域，静态方法，构造器等）被调用时发生
}
