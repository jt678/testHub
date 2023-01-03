package com.jt.test.demo1.service.impl;

import com.jt.test.demo1.service.RentHouseService;

/**
 * RentHouse
 * 房东直租
 * @Author: jt
 * @Date: 2022/12/23 14:27
 */
public class RentHouse implements RentHouseService {
    @Override
    public String rentHouse() {
        System.out.println("租房完成!");
        return "租房完成";
    }
}
