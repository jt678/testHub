package com.jt.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.test.common.HttpResult;
import com.jt.test.common.PageInfo;
import com.jt.test.domain.entity.TestData;
import com.jt.test.service.TestDataService;
import com.jt.test.utils.SqlIdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public HttpResult<List<TestData>> datatest(PageInfo info) {
        IPage<TestData> page = new Page<>(info.getPageNum(), info.getPageSize());
        IPage<TestData> result = testDataService.page(page, new LambdaQueryWrapper<TestData>().gt(TestData::getId, 1l));
        List<TestData> infoList = result.getRecords();
        return HttpResult.success(result.getTotal(), infoList);


    }

    /**
     * 写入雪花id
     */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/init")
    @ApiOperation("写入雪花id")
    public HttpResult init() {
        try {
            List<TestData> testDataList = testDataService.list();
            List<Long> idList = testDataList.stream().map(TestData::getId).collect(Collectors.toList());
            ArrayList<TestData> targetList = new ArrayList<>();
            for (TestData data : testDataList) {
                data.setId(SqlIdUtils.getId());
                targetList.add(data);
            }
            testDataService.removeByIds(idList);
            testDataService.saveBatch(targetList) ;

        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.failed("写入失败");
        }
        return HttpResult.success("写入成功");
    }


    /**
     * 测试雪花ID精度丢失问题
     */
    @ApiOperation("测试雪花ID精度丢失问题")
    @GetMapping("/id")
    public HttpResult<List<TestData>> SnowIdTest(String id) {

        LambdaQueryWrapper<TestData> dataLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dataLambdaQueryWrapper.eq(TestData::getId,id);
//        dataLambdaQueryWrapper.last("limit 10");
        List<TestData> testDataList = testDataService.list(dataLambdaQueryWrapper);
        return HttpResult.success(testDataList);
    }
}
