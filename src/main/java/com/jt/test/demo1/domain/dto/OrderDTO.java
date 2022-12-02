package com.jt.test.demo1.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * OrderDTO
 *
 * @author jt
 * @date 2022/4/28
 **/
@ApiModel("查询条件处理")
@Data
public class OrderDTO {
    @ApiModelProperty("查询条件：多条件，用,分开------------如：id,memberUsername,billType")
    private List<String> conditions;

    @ApiModelProperty("收货人姓名")
    private String receiverName;

}
