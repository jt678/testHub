package com.jt.test.demo1.service.impl;

import com.jt.test.demo1.domain.Coffee;
import com.jt.test.demo1.service.CoffeAbstractFactory;

/**
 * CoffeeStore
 * 业务方法,在这里根据初始化传入的具体factory，调用其方法产生不同的具体产品
 * @Author: jt
 * @Date: 2023/9/4 16:03
 */
public class CoffeeStore {
   private CoffeAbstractFactory coffeFactory;

   public CoffeeStore(CoffeAbstractFactory coffeFactory){
       this.coffeFactory = coffeFactory;
   }

   public Coffee orderCoffee(){
       Coffee coffee = coffeFactory.makeCoffee();
       coffee.addMilk();
       coffee.addSuger();
       return coffee;
   }
}
