package com.jt.test.demo1.service.impl;

import com.jt.test.demo1.domain.AmericanoCoffee;
import com.jt.test.demo1.domain.Coffee;
import com.jt.test.demo1.service.CoffeAbstractFactory;

/**
 * AmericanoFactroy
 * 美式咖啡工厂
 * @Author: jt
 * @Date: 2023/9/4 15:41
 */
public class AmericanoFactroy implements CoffeAbstractFactory {
    @Override
    public Coffee makeCoffee() {
        return  new AmericanoCoffee();
    }
}
