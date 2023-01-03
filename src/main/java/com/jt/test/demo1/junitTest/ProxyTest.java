package com.jt.test.demo1.junitTest;

import com.jt.test.demo1.service.RentHouseService;
import com.jt.test.demo1.service.impl.IntermediaryProxy;
import com.jt.test.demo1.service.impl.RentHouse;
import org.junit.Test;

/**
 * StaticProxyTest
 * 静态代理模式
 * @Author: jt
 * @Date: 2022/12/23 14:34
 */
public class ProxyTest {
    @Test
    public void staticProxyTest(){
        RentHouseService rentHouseService = new RentHouse();
        RentHouseService intermediaryProxy = new IntermediaryProxy(rentHouseService);
        String result = intermediaryProxy.rentHouse();
        System.out.println(result);
    }
}
