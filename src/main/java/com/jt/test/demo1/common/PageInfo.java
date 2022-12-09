package com.jt.test.demo1.common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * PageInfo
 *
 * @author jt
 * @date 2022/4/28
 **/
@Data
@ApiModel("分页属性")
public class PageInfo {
    @NotNull(message = "分页参数不能为空")
    private Long pageSize = 1000L;
    @NotNull(message = "分页参数不能为空")
    private Long pageNum = 1L;
}
