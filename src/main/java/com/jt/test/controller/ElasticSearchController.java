package com.jt.test.controller;

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
 * @Date: 2022/9/13 9:08
 */
@RestController
@Api(tags = "集成es8")
@RequestMapping("/es")
public class ElasticSearchController {


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
    public HttpResult addIndex(String indexName) throws IOException {

        return esHelper.addIndex(indexName);
    }

    /**
     * 通过索引名查看索引信息
     *
     * @return
     */
    @ApiOperation("通过索引名查看索引信息")
    @GetMapping("/getIndex")
    public HttpResult getIndex(String indexName,String id) throws IOException {

        return esHelper.getIndex(indexName,id);
    }

    /**
     * 查看所有索引信息
     */
    @ApiOperation("查看所有索引信息")
    @GetMapping("/getAllIndex")
    public HttpResult getAllIndex() {

        return esHelper.getAllIndex();
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
     * 批量删除文档
     */
    @ApiOperation("批量删除文档")
    @GetMapping("/deleteDoc")
    public HttpResult deleteDoc(String ids, String indexName) throws IOException {

        return esHelper.deleteDoc(ids, indexName);
    }
}
