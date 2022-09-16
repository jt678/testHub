package com.jt.test.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.jt.test.common.HttpResult;
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
@Api(tags = "es8搜索操作接口")
@RequestMapping("/es/search")
public class ElasticSearchController {
    @Autowired
    private ElasticSearchHelper esHelper;
    /**
     * 分页查询全文档
     */
    @GetMapping("/list")
    @ApiOperation("分页查询全文档")
    public HttpResult list(String indexName,Integer pageNum,Integer pageSize) throws IOException {
        return esHelper.list(indexName,pageNum,pageSize);
    }

    /**
     * 数据过滤，返回指定字段
     */
    @GetMapping("/filter")
    @ApiOperation("数据过滤，返回指定字段")
    public HttpResult filter(String indexName,String includeFields) throws IOException {
        return esHelper.filter(indexName,includeFields);
    }

    /**
     * match查找，关键字分词
     */
    @GetMapping("/match")
    @ApiOperation("match查找，关键字分词")
    public HttpResult match(String indexName,String field,String keyWord,Integer pageNum, Integer pageSize) throws IOException {
        return esHelper.match(indexName,field,keyWord,pageNum,pageSize);
    }


}
