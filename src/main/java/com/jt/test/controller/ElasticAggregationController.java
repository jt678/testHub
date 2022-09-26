package com.jt.test.controller;

import com.jt.test.common.HttpResult;
import com.jt.test.domain.bo.ElasticSearchBO;
import com.jt.test.helper.ElasticSearchHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * ElasticAggregationController
 *
 * @Author: jt
 * @Date: 2022/9/19 15:49
 */
@RestController
@Api(tags = "es8聚合操作")
@RequestMapping("/es/aggregation")
public class ElasticAggregationController {
    @Autowired
    private ElasticSearchHelper esHelper;

    /**
     * 查max,min,avg（单个）
     * @param bo
     * @return
     * @throws IOException
     */
    @ApiOperation("查max,min,avg（单个）")
    @PostMapping("/common")
    public HttpResult common(ElasticSearchBO bo) throws IOException {
        return esHelper.common(bo);
    }

    /**
     * 统计（所有的如max，min，sum都查出来）
     * @param bo
     * @return
     * @throws IOException
     */
    @ApiOperation("统计（所有的如max，min，sum都查出来）")
    @PostMapping("/count")
    public HttpResult count(ElasticSearchBO bo) throws IOException {
        return esHelper.count(bo);
    }

    /**
     * 分类
     * @param bo
     * @return
     * @throws IOException
     */
    @ApiOperation("分类(还有问题)")
    @PostMapping("/group")
    public HttpResult group(ElasticSearchBO bo) throws IOException{
        return esHelper.group(bo);
    }

    /**
     * 去重
     */
    @ApiOperation("去重")
    @PostMapping("/cardinate")
    public HttpResult cardinate(ElasticSearchBO bo) throws IOException {
        return esHelper.cardinate(bo);
    }

}
