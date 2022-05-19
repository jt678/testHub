package com.jt.test.helper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.test.common.HttpResult;
import com.jt.test.convert.BrandConvert;
import com.jt.test.domain.Brand;
import com.jt.test.domain.bo.BrandBO;
import com.jt.test.domain.dto.BrandDTO;
import com.jt.test.domain.vo.BrandVO;
import com.jt.test.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BrandHelper
 *
 * @author jt
 * @date 2022/5/18
 **/
@Service
public class BrandHelper {
    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandConvert convert;

    public HttpResult<List<BrandVO>> list(BrandBO bo) {
        //测试需求：分页按条件查询品牌，且条件有一个是复合条件
        List<BrandVO> voList = new ArrayList();
        //先拿出来所有的数据，然后分页
        IPage<Brand> page = new Page<>(bo.getPageNum(),bo.getPageSize());
        LambdaQueryWrapper<Brand> lambda = new QueryWrapper<Brand>().lambda()
//                .eq(Brand::getName,bo.getName())
                .eq(Brand::getFirstLetter,bo.getFirstLetter())
                .between(Brand::getSort,0,200)
                ;
//        BrandDTO dto = convert.bo2Dto(bo);
//        LambdaQueryWrapper<Brand> lambda = new QueryWrapper<Brand>().lambda()
//                .eq(Brand::getLogo,dto.getCondition())
//                .or()
//                .eq(Brand::getName,dto.getCondition())
//                .or()
//                .eq(Brand::getFirstLetter,dto.getCondition())
//                .or()
//                .eq(Brand::getBigPic,dto.getCondition())
//                ;
        IPage<Brand> result = brandService.page(page,lambda);
        List<Brand> records = result.getRecords();
        voList = convert.entities2vos(records);
        return HttpResult.success(result.getTotal(),voList);
    }
    /**
     *
     */
}
