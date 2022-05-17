package com.jt.test.controller;

import com.jt.test.service.impl.SimpleAspectTestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * SimpleAspectController
 *
 * @author jt
 * @date 2022/5/17
 **/
@Controller
public class SimpleAspectController {
    @Autowired
    private SimpleAspectTestServiceImpl simpleAspectTestService;
    public void  aspectTest(){
        simpleAspectTestService.addAspect();
    }
}
