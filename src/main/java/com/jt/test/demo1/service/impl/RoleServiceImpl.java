package com.jt.test.demo1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.test.demo1.domain.entity.Role;
import com.jt.test.demo1.service.RoleService;
import com.jt.test.demo1.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【ums_role(后台用户角色表)】的数据库操作Service实现
* @createDate 2022-08-03 13:49:10
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




