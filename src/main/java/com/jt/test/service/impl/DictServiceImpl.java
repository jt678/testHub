package com.jt.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.test.domain.entity.Dict;
import com.jt.test.service.DictService;
import com.jt.test.mapper.DictMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【t_dict】的数据库操作Service实现
* @createDate 2022-10-28 17:20:03
*/
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict>
    implements DictService{

}




