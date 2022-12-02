package com.jt.test.demo1.helper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.test.demo1.common.HttpResult;
import com.jt.test.demo1.convert.OrderConvert;
import com.jt.test.demo1.domain.entity.Order;
import com.jt.test.demo1.domain.bo.OrderBO;
import com.jt.test.demo1.domain.dto.OrderDTO;
import com.jt.test.demo1.domain.vo.OrderVO;

import com.jt.test.demo1.service.MemberPriceService;
import com.jt.test.demo1.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderHelper
 *
 * @author jt
 * @date 2022/3/8
 **/
@Service
@Slf4j
public class OrderHelper {

    @Autowired
    OrderService service;
    @Autowired
    OrderConvert convert;
    @Autowired
    MemberPriceService memberPriceService;

    public OrderVO test(Long id) {

        Order order = service.getById(id);
        OrderVO orderVO = convert.entityToVO(order);
        return orderVO;
    }

    /**
     * 条件查询
     *
     * @return
     */
    public HttpResult<List<OrderVO>>  list(OrderBO bo) {
        List<OrderVO> voList = new ArrayList<>();
        OrderDTO orderDTO = convert.boToDto(bo);

        //查entity数据
        IPage<Order> page = new Page<>(bo.getPageNum(), bo.getPageSize());
        IPage<Order> result = service.listBySearch(page, orderDTO);

        List<Order> orderList = result.getRecords();
//        List<Order> orderList = service.listBySearch();
        voList = convert.entityListToVoList(orderList);
        return HttpResult.success(result.getTotal(),voList);
    }

}
