package com.jt.test.demo1.convert;

import com.jt.test.demo1.domain.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * RoleConvert
 *
 * @Author: jt
 * @Date: 2022/11/28 11:45
 */
@Mapper(componentModel = "spring")
public interface RoleConvert {
    List<Role> test2New(List<Role> testDataList);
}
