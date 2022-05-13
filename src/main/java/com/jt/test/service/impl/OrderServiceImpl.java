package com.jt.test.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.test.domain.entity.Order;
import com.jt.test.domain.dto.OrderDTO;
import com.jt.test.service.OrderService;
import com.jt.test.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author j
* @description 针对表【oms_order(订单表)】的数据库操作Service实现
* @createDate 2022-03-07 17:46:56
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    @Autowired
    private OrderMapper mapper;
    @Override
    public IPage<Order> listBySearch(IPage<Order> page, OrderDTO orderDTO) {
        return mapper.listBySearch(page,orderDTO);
    }
}




