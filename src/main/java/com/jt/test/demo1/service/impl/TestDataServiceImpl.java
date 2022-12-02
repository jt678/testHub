package com.jt.test.demo1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.test.demo1.domain.entity.TestData;
import com.jt.test.demo1.service.TestDataService;
import com.jt.test.demo1.mapper.TestDataMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【test_data】的数据库操作Service实现
* @createDate 2022-08-09 16:24:27
*/
@Service
public class TestDataServiceImpl extends ServiceImpl<TestDataMapper, TestData>
    implements TestDataService{

}




