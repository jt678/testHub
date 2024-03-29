package com.jt.test.demo1.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jt.test.demo1.domain.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.test.demo1.domain.dto.OrderDTO;
import org.apache.ibatis.annotations.Param;

/**
* @author j
* @description 针对表【oms_order(订单表)】的数据库操作Mapper
* @createDate 2022-03-07 17:46:56
* @Entity com.jt.test.demo1.domain.entity.Order
*/
public interface OrderMapper extends BaseMapper<Order> {

    IPage<Order> listBySearch(@Param("page") IPage<Order> page,
                              @Param("dto") OrderDTO orderDTO);
}




