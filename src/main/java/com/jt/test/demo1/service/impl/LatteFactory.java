package com.jt.test.demo1.service.impl;

import com.jt.test.demo1.domain.Coffee;
import com.jt.test.demo1.domain.LatteCoffee;
import com.jt.test.demo1.service.CoffeAbstractFactory;

/**
 * LatteFactory
 * 拿铁工厂
 * @Author: jt
 * @Date: 2023/9/4 15:59
 */
public class LatteFactory implements CoffeAbstractFactory {
    @Override
    public Coffee makeCoffee() {
        return  new LatteCoffee();
    }
}
