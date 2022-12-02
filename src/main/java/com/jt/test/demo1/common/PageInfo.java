package com.jt.test.demo1.common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * PageInfo
 *
 * @author jt
 * @date 2022/4/28
 **/
@Data
@ApiModel("分页属性")
public class PageInfo {

    private Long pageSize = 1000L;

    private Long pageNum = 1L;
}
