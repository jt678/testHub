package com.jt.test.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jt.test.domain.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jt.test.domain.dto.OrderDTO;

/**
* @author j
* @description 针对表【oms_order(订单表)】的数据库操作Service
* @createDate 2022-03-07 17:46:56
*/
public interface OrderService extends IService<Order> {

    IPage<Order> listBySearch(IPage<Order> page, OrderDTO orderDTO);
}
