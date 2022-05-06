package com.jt.test.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jt.test.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.test.domain.dto.OrderDTO;
import org.apache.ibatis.annotations.Param;

/**
* @author j
* @description 针对表【oms_order(订单表)】的数据库操作Mapper
* @createDate 2022-03-07 17:46:56
* @Entity com.jt.test.domain.Order
*/
public interface OrderMapper extends BaseMapper<Order> {

    IPage<Order> listBySearch(@Param("page") IPage<Order> page,
                              @Param("dto") OrderDTO orderDTO);
}




