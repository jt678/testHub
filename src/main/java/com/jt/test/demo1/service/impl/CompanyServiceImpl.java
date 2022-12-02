package com.jt.test.demo1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.test.demo1.domain.entity.Company;
import com.jt.test.demo1.service.CompanyService;
import com.jt.test.demo1.mapper.CompanyMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【gate_company】的数据库操作Service实现
* @createDate 2022-06-27 14:59:21
*/
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company>
    implements CompanyService{

}




