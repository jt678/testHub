package com.jt.test.demo1.domain.bo;

import com.jt.test.demo1.domain.SchoolInfo;
import lombok.Data;

/**
 * HttpRequestBO
 *
 * @Author: jt
 * @Date: 2022/11/23 14:27
 */
@Data
public class HttpRequestBO {
    private Integer pageSize;
    private Integer pageNum;

    private SchoolInfo schoolInfo;
    

}
