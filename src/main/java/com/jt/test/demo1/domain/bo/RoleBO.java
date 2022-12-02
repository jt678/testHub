package com.jt.test.demo1.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RoleBO
 *
 * @Author: jt
 * @Date: 2022/8/3 14:16
 */
@Data
@ApiModel("导出筛选条件")
public class RoleBO {
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("启用状态：0->禁用；1->启用")
    private Integer status;
}
