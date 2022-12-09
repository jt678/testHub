package com.jt.test.demo1.domain.bo;

import com.jt.test.demo1.common.PageInfo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * BrandBO
 *
 * @author jt
 * @date 2022/5/18
 **/
@Data
public class BrandBO extends PageInfo {
    /**
     * 品牌名称
     */
    @NotEmpty(message = "品牌名称 不能为空")
    private String name;
    /**
     * 首字母
     */
    private String firstLetter;
    /**
     * 品牌logo
     */
    private String logo;

    /**
     * product_comment_count and product_count
     */

    private String condition;
}
