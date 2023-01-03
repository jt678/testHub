package com.jt.test.demo1.service.impl;

import com.jt.test.demo1.service.RentHouseService;

/**
 * IntermediaryProxy
 * 中介代理
 * @Author: jt
 * @Date: 2022/12/23 14:28
 */
public class IntermediaryProxy implements RentHouseService {
    private RentHouseService rentHouseService;
    public IntermediaryProxy(RentHouseService rentHouse){
            rentHouseService = rentHouse;
    }

    @Override
    public String rentHouse() {
        System.out.println("交中介费");
        rentHouseService.rentHouse();
        System.out.println("中介负责维修工作");
        return "交了中介费，租房完成";
    }
}
