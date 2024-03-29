package com.jt.test.demo1.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * UserVO
 *
 * @Author: jt
 * @Date: 2022/7/6 14:21
 */
@Data
@ApiModel("微信公众号关注账号基础信息VO")
public class UserVO {
    private Integer subscribe;

    private String openid;

    private String language;

    @ApiModelProperty("订阅时间（时间戳入参）")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date subscribeTime;

    private String unionid;

    private String remark;

    private Integer groupid;

    private Integer[] tagidList;

    private String subscribeScene;

    private Integer qrScene;

    private String qrSceneStr;
}
