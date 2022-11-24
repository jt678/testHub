package com.jt.test.domain.bo;

import com.jt.test.domain.SchoolInfo;
import com.jt.test.domain.entity.Company;
import lombok.Data;

import java.util.List;

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
