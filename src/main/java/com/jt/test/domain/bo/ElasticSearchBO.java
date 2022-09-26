package com.jt.test.domain.bo;

import com.jt.test.common.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * EelasticSearchBO
 *
 * @Author: jt
 * @Date: 2022/9/19 13:49
 */
@Data
@ApiModel("es搜索入参")
public class ElasticSearchBO extends PageInfo {

    @ApiModelProperty("索引名")
    private String indexName;

    @ApiModelProperty("字段")
    private String field;

    @ApiModelProperty("多字段")
    private String fields;

    @ApiModelProperty("搜索值")
    private String keyWord;

    @ApiModelProperty("文档多Id")
    private String ids;

}
