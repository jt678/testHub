package com.jt.test.domain.vo;

import com.jt.test.domain.entity.Company;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * IndexInfo
 *
 * @Author: jt
 * @Date: 2022/9/14 16:32
 */
@Data
public class IndexInfoVO {

    private List<Map<String,Object>> propertieList;

    //setting
    private String indexName;

    private String numberOfShards;

    private String numberOfReplicas;

    private String creationDate;

    private String uuid;
}
