package com.jt.test.service.impl;

import com.jt.test.service.SimpleAspectTestService;
import org.springframework.stereotype.Service;

/**
 * SimpleAspectTestServiceImpl
 *
 * @author jt
 * @date 2022/5/17
 **/
@Service
public class SimpleAspectTestServiceImpl implements SimpleAspectTestService {
    @Override
    public void addAspect() {
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("切面测试。。。");
    }
}
