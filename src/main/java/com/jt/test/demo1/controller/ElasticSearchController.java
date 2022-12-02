package com.jt.test.demo1.controller;

import com.jt.test.demo1.common.HttpResult;
import com.jt.test.demo1.domain.bo.ElasticSearchBO;
import com.jt.test.demo1.helper.ElasticSearchHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * ElasticSearchController
 *
 * @Author: jt
 * @Date: 2022/9/15 15:06
 */
@RestController
@Api(tags = "es8基本搜索操作接口")
@RequestMapping("/es/search")
public class ElasticSearchController {
    @Autowired
    private ElasticSearchHelper esHelper;

    /**
     * 分页查询全文档
     */
    @PostMapping("/list")
    @ApiOperation("分页查询全文档")
    public HttpResult list(ElasticSearchBO bo) throws IOException {
        return esHelper.list(bo);
    }

    /**
     * 数据过滤，返回指定字段
     */
    @PostMapping("/filter")
    @ApiOperation("数据过滤，返回指定字段")
    public HttpResult filter(ElasticSearchBO bo) throws IOException {
        return esHelper.filter(bo);
    }

    /**
     * match查找，关键字分词
     */
    @PostMapping("/match")
    @ApiOperation("match查找，关键字分词")
    public HttpResult match(ElasticSearchBO bo) throws IOException {
        return esHelper.match(bo);
    }

    /**
     * Term精确查询
     */
    @PostMapping("/term")
    @ApiOperation("Term精确查询")
    public HttpResult term(ElasticSearchBO bo) throws IOException {
        return esHelper.term(bo);
    }

    /**
     * range范围查询
     */
    @PostMapping("/range")
    @ApiOperation("range范围查询")
    public HttpResult range(ElasticSearchBO bo) throws IOException {
        return esHelper.range(bo);
    }

    /**
     * fuzzy模糊查询
     */
    @PostMapping("/fuzzy")
    @ApiOperation("模糊查询")
    public HttpResult fuzzy(ElasticSearchBO bo) throws IOException {
        return esHelper.fuzzy(bo);
    }

    /**
     * 多个id查询
     */
    @ApiOperation("多个id查询")
    @PostMapping("/ids")
    public HttpResult ids(ElasticSearchBO bo) throws IOException {
        return esHelper.ids(bo);
    }

    /**
     * （重点）高亮查询
     */
    @ApiOperation("（重点）高亮查询")
    @PostMapping("/highLight")
    public HttpResult highLight(ElasticSearchBO bo) throws IOException {
        return esHelper.highLight(bo);
    }

    /**
     * 布尔查询Bool
     */
    @ApiOperation("布尔查询Bool")
    @PostMapping("/booleanSearch")
    public HttpResult booleanSearch(ElasticSearchBO bo) throws IOException {
        return esHelper.booleanSearch(bo);
    }

}
