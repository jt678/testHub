package com.jt.test.demo1.helper;

import com.jt.test.demo1.domain.entity.MemberPrice;
import com.jt.test.demo1.domain.entity.Order;
import com.jt.test.demo1.service.MemberPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Future;

/**
 * memberPriceHelper
 *
 * @Author: jt
 * @Date: 2023/7/19 14:30
 */
@Service
public class MemberPriceHelper {
    @Autowired
    private MemberPriceService memberPriceService;


    @Async
    @Transactional(rollbackFor = Exception.class)
    public Future<String> dealMemberPrice1(Order order) {
        if (order.getOrderSn().equals("c1")){
            MemberPrice memberPrice = new MemberPrice();
            memberPrice.setMemberLevelName("c1等级");
            memberPriceService.save(memberPrice);
        }
        return new AsyncResult<String>("异步任务1完成");
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    public Future<String> dealMemberPrice2(Order order) {
        if (order.getOrderSn().equals("c2")){
            MemberPrice memberPrice = new MemberPrice();
            memberPrice.setMemberLevelName("c2等级");
            memberPriceService.save(memberPrice);
        }
        return new AsyncResult<String>("异步任务2完成");
    }
}
