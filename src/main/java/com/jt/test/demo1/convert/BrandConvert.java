package com.jt.test.demo1.convert;


import com.jt.test.demo1.domain.entity.Brand;
import com.jt.test.demo1.domain.bo.BrandBO;
import com.jt.test.demo1.domain.dto.BrandDTO;
import com.jt.test.demo1.domain.vo.BrandVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * BrandConvert
 *
 * @author jt
 * @date 2022/5/18
 **/
@Mapper(componentModel = "spring")
public interface BrandConvert {


    List<BrandVO> entities2vos(List<Brand> records);

    @Mapping(target = "condition",expression = "java(cn.hutool.core.util.StrUtil.isNotEmpty(bo.getCondition())?java.util.Arrays.asList(bo.getCondition().split(\",\")):null)")
    BrandDTO bo2Dto(BrandBO bo);
}
