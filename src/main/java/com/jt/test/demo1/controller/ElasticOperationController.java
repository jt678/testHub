package com.jt.test.demo1.controller;

import com.jt.test.demo1.common.HttpResult;
import com.jt.test.demo1.helper.ElasticSearchHelper;
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
 * @Date: 2022/9/13 9:08
 */
@RestController
@Api(tags = "集成es8，对索引和文档的操作接口")
@RequestMapping("/es/operation")
public class ElasticOperationController {


    @Autowired
    private ElasticSearchHelper esHelper;

    /**
     * 创建索引
     *
     * @return
     * @throws IOException
     */
    @ApiOperation("创建索引")
    @GetMapping("/addIndex")
    public HttpResult addIndex(String indexName, String numberOfShards, String numberOfReplicas, String refreshInterval) throws IOException {

        return esHelper.addIndex(indexName, numberOfShards, numberOfReplicas, refreshInterval);
    }

    /**
     * 删除索引
     */
    @ApiOperation("删除索引")
    @GetMapping("/deleteIndex")
    public HttpResult deleteIndex(String indexName) throws IOException {
        return esHelper.deleteIndex(indexName);
    }

    /**
     * 根据索引名查索引
     */
    @ApiOperation("根据索引名查索引")
    @GetMapping("/getIndex")
    public HttpResult getIndex(String indexName) throws IOException {
        return esHelper.getIndex(indexName);
    }

    /**
     * 查看所有索引
     */
    @ApiOperation("查看所有索引")
    @GetMapping("/indexList")
    public HttpResult indexList() throws IOException {
        return esHelper.indexList();
    }

    /**
     * 批量添加文档
     */
    @ApiOperation("批量添加文档")
    @GetMapping("/importDoc")
    public HttpResult importDoc(String indexName) throws IOException {

        return esHelper.importDoc(indexName);
    }

    /**
     * 根据id批量删除文档
     */
    @ApiOperation("根据id批量删除文档")
    @GetMapping("/deleteDoc")
    public HttpResult deleteDoc(String ids, String indexName) throws IOException {

        return esHelper.deleteDoc(ids, indexName);
    }

    /**
     * 通过索引名查看索引文档信息
     *
     * @return
     */
    @ApiOperation("通过索引名查看索引文档信息")
    @GetMapping("/getIndexDoc")
    public HttpResult getIndexDoc(String indexName, String id) throws IOException {

        return esHelper.getIndexDoc(indexName, id);
    }
}
