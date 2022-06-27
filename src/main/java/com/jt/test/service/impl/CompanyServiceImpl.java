package com.jt.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.test.domain.Company;
import com.jt.test.service.CompanyService;
import com.jt.test.mapper.CompanyMapper;
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




