package com.jt.test.demo1.domain.dto;

import com.jt.test.demo1.common.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * BrandDTO
 *
 * @author jt
 * @date 2022/5/19
 **/
@Data
public class BrandDTO extends PageInfo {
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

    private List<String> condition;
}
