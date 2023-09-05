package com.jt.test.demo1.service.factory;

import com.jt.test.demo1.domain.Coffee;

/**
 * CoffeAbstractFactory
 * 咖啡抽象工厂
 * @Author: jt
 * @Date: 2023/9/4 15:38
 */
public interface CoffeAbstractFactory {
    Coffee makeCoffee();
}
