package com.jt.test.controller;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.jt.test.common.HttpResult;
import com.jt.test.common.PageInfo;
import com.jt.test.domain.bo.EelasticSearchBO;
import com.jt.test.helper.ElasticSearchHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/list")
    @ApiOperation("分页查询全文档")
    public HttpResult list(EelasticSearchBO bo) throws IOException {
        return esHelper.list(bo);
    }

    /**
     * 数据过滤，返回指定字段
     */
    @GetMapping("/filter")
    @ApiOperation("数据过滤，返回指定字段")
    public HttpResult filter(EelasticSearchBO bo) throws IOException {
        return esHelper.filter(bo);
    }

    /**
     * match查找，关键字分词
     */
    @GetMapping("/match")
    @ApiOperation("match查找，关键字分词")
    public HttpResult match(EelasticSearchBO bo) throws IOException {
        return esHelper.match(bo);
    }

    /**
     * Term精确查询
     */
    @GetMapping("/term")
    @ApiOperation("Term精确查询")
    public HttpResult term(EelasticSearchBO bo) throws IOException {
        return esHelper.term(bo);
    }

    /**
     * range范围查询
     */
    @GetMapping("/range")
    @ApiOperation("range范围查询")
    public HttpResult range(EelasticSearchBO bo) throws IOException {
        return esHelper.range(bo);
    }

    /**
     * fuzzy模糊查询
     */
    @GetMapping("/fuzzy")
    @ApiOperation("模糊查询")
    public HttpResult fuzzy(EelasticSearchBO bo) throws IOException {
        return esHelper.fuzzy(bo);
    }

    /**
     * 多个id查询
     */
    @ApiOperation("多个id查询")
    @GetMapping("/ids")
    public HttpResult ids(EelasticSearchBO bo) throws IOException {
        return esHelper.ids(bo);
    }

    /**
     * （重点）高亮查询
     */
    @ApiOperation("（重点）高亮查询")
    @GetMapping("/highLight")
    public HttpResult highLight(EelasticSearchBO bo) throws IOException {
        return esHelper.highLight(bo);
    }

    /**
     * 布尔查询Bool
     */
    @ApiOperation("布尔查询Bool")
    @GetMapping("/booleanSearch")
    public HttpResult booleanSearch(EelasticSearchBO bo) throws IOException {
        return esHelper.booleanSearch(bo);
    }

}
