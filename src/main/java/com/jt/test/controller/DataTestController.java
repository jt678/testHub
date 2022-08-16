package com.jt.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.test.common.HttpResult;
import com.jt.test.common.PageInfo;
import com.jt.test.domain.entity.TestData;
import com.jt.test.service.TestDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DataTestController
 *
 * @Author: jt
 * @Date: 2022/8/9 16:37
 */
@RestController
@Api(tags = "数据测试")
@RequestMapping("/data")
public class DataTestController {
    @Autowired
    private TestDataService testDataService;

    @ApiOperation("测试")
    @PostMapping("/test")
    public HttpResult<List<TestData>> datatest(PageInfo info){
        IPage<TestData> page = new Page<>(info.getPageNum(),info.getPageSize());
        IPage<TestData> result = testDataService.page(page,new LambdaQueryWrapper<TestData>().gt(TestData::getId,1l));
        List<TestData> infoList  = result.getRecords();
        return  HttpResult.success(result.getTotal(),infoList);


    }
}
