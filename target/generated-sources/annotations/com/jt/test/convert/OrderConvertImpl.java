package com.jt.test.convert;

import com.jt.test.domain.entity.Order;
import com.jt.test.domain.bo.OrderBO;
import com.jt.test.domain.dto.OrderDTO;
import com.jt.test.domain.vo.OrderVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-06T14:11:43+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_312 (International Business Machines Corporation)"
)
@Component
public class OrderConvertImpl implements OrderConvert {

    @Override
    public OrderVO entityToVO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderVO orderVO = new OrderVO();

        orderVO.setId( order.getId() );
        orderVO.setMemberId( order.getMemberId() );
        orderVO.setCouponId( order.getCouponId() );
        orderVO.setOrderSn( order.getOrderSn() );
        orderVO.setCreateTime( order.getCreateTime() );
        orderVO.setMemberUsername( order.getMemberUsername() );
        orderVO.setTotalAmount( order.getTotalAmount() );
        orderVO.setPayAmount( order.getPayAmount() );
        orderVO.setFreightAmount( order.getFreightAmount() );
        orderVO.setPromotionAmount( order.getPromotionAmount() );
        orderVO.setIntegrationAmount( order.getIntegrationAmount() );
        orderVO.setCouponAmount( order.getCouponAmount() );
        orderVO.setDiscountAmount( order.getDiscountAmount() );
        orderVO.setPayType( order.getPayType() );
        orderVO.setSourceType( order.getSourceType() );
        orderVO.setStatus( order.getStatus() );
        orderVO.setOrderType( order.getOrderType() );
        orderVO.setDeliveryCompany( order.getDeliveryCompany() );
        orderVO.setDeliverySn( order.getDeliverySn() );
        orderVO.setAutoConfirmDay( order.getAutoConfirmDay() );
        orderVO.setIntegration( order.getIntegration() );
        orderVO.setGrowth( order.getGrowth() );
        orderVO.setPromotionInfo( order.getPromotionInfo() );
        orderVO.setBillType( order.getBillType() );
        orderVO.setBillHeader( order.getBillHeader() );
        orderVO.setBillContent( order.getBillContent() );
        orderVO.setBillReceiverPhone( order.getBillReceiverPhone() );
        orderVO.setBillReceiverEmail( order.getBillReceiverEmail() );
        orderVO.setReceiverName( order.getReceiverName() );
        orderVO.setReceiverPhone( order.getReceiverPhone() );
        orderVO.setReceiverPostCode( order.getReceiverPostCode() );
        orderVO.setReceiverProvince( order.getReceiverProvince() );
        orderVO.setReceiverCity( order.getReceiverCity() );
        orderVO.setReceiverRegion( order.getReceiverRegion() );
        orderVO.setReceiverDetailAddress( order.getReceiverDetailAddress() );
        orderVO.setNote( order.getNote() );
        orderVO.setConfirmStatus( order.getConfirmStatus() );
        orderVO.setDeleteStatus( order.getDeleteStatus() );
        orderVO.setUseIntegration( order.getUseIntegration() );
        orderVO.setPaymentTime( order.getPaymentTime() );
        orderVO.setDeliveryTime( order.getDeliveryTime() );
        orderVO.setReceiveTime( order.getReceiveTime() );
        orderVO.setCommentTime( order.getCommentTime() );
        orderVO.setModifyTime( order.getModifyTime() );

        return orderVO;
    }

    @Override
    public OrderDTO boToDto(OrderBO bo) {
        if ( bo == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setReceiverName( bo.getReceiverName() );

        orderDTO.setConditions( cn.hutool.core.util.StrUtil.isNotEmpty(bo.getConditions())? java.util.Arrays.asList(bo.getConditions().toUpperCase().split(",")):null );

        return orderDTO;
    }

    @Override
    public List<OrderVO> entityListToVoList(List<Order> orderList) {
        if ( orderList == null ) {
            return null;
        }

        List<OrderVO> list = new ArrayList<OrderVO>( orderList.size() );
        for ( Order order : orderList ) {
            list.add( entityToVO( order ) );
        }

        return list;
    }
}
