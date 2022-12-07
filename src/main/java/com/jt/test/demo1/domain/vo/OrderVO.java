package com.jt.test.demo1.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jt.test.demo1.config.TimetostringSerialize;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OrderVO
 *
 * @author jt
 * @date 2022/3/8
 **/
@ApiModel("订单vo")
public class OrderVO {
    /**
     * 订单id
     */

    private Long id;

    /**
     *
     */
    private Long memberId;

    /**
     *
     */
    private Long couponId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 提交时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间(时间戳)
     */
//    @JsonSerialize(using = TimetostringSerialize.class)
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
