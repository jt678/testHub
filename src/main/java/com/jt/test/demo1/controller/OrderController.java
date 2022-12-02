package com.jt.test.demo1.controller;
import com.jt.test.demo1.common.HttpResult;
import com.jt.test.demo1.domain.bo.OrderBO;
import com.jt.test.demo1.domain.vo.OrderVO;
import com.jt.test.demo1.helper.OrderHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * OrderController
 *
 * @author jt
 * @date 2022/3/8
 **/
@Controller
@RequestMapping("/test")
@Api(tags = "测试")
public class OrderController {
    @Autowired
    private OrderHelper helper;

    @GetMapping("/{id}")
    @ResponseBody
    public OrderVO test(@PathVariable("id")@ApiParam("订单id")Long id){

        return helper.test(id);
    }

    @PostMapping("/list")
    @ResponseBody
    public HttpResult<List<OrderVO>>  list(OrderBO bo){

       return helper.list(bo);
    }
}
