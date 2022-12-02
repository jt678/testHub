package com.jt.test.demo1.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * SendBO
 *
 * @Author: jt
 * @Date: 2022/8/8 14:18
 */
@Data
@ApiModel("邮件功能入参")
public class SendBO {

    @ApiModelProperty("收件人邮箱地址--------user1,user2,....")
    private String toUserAddress;

    @ApiModelProperty("内容填充物----对应人名")
    private String content;

}
