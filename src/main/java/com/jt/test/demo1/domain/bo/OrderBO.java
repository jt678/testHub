package com.jt.test.demo1.domain.bo;

import com.jt.test.demo1.common.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * OrderBO
 *
 * @author jt
 * @date 2022/4/28
 **/
@ApiModel("查询条件")
@Data
public class OrderBO extends PageInfo {
    @ApiModelProperty("查询条件：多条件，用,分开------------如：id,memberUsername,billType")
    private String conditions;

    @ApiModelProperty("收货人姓名")
    private String receiverName;
}
