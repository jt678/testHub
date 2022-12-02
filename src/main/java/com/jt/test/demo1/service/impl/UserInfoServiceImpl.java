package com.jt.test.demo1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.test.demo1.domain.entity.UserInfo;
import com.jt.test.demo1.service.UserInfoService;
import com.jt.test.demo1.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【wx_user_info】的数据库操作Service实现
* @createDate 2022-07-08 09:55:31
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




