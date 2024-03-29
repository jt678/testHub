package com.jt.test.demo1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.test.demo1.domain.entity.Brand;
import com.jt.test.demo1.service.BrandService;
import com.jt.test.demo1.mapper.BrandMapper;
import org.springframework.stereotype.Service;

/**
* @author j
* @description 针对表【pms_brand(品牌表)】的数据库操作Service实现
* @createDate 2022-05-18 17:03:31
*/
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand>
    implements BrandService{

}




