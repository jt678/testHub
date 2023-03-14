package com.jt.test.demo1.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * JgjAppuserVO
 *
 * @Author: jt
 * @Date: 2023/2/23 9:23
 */
@Data
public class JgjAppuserVO {

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ExcelProperty(value = "密码")
    private String password;
    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String mobile;
    /**
     * 用户类型
     */
    @ExcelProperty(value = "用户类型")
    private Integer userType;
    /**
     * 民警用户所属检查站
     */
    @ExcelProperty(value = "民警用户所属检查站")
    private String checkId;
}
