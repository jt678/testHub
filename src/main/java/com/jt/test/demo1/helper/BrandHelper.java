package com.jt.test.demo1.helper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.test.demo1.common.HttpResult;
import com.jt.test.demo1.convert.BrandConvert;
import com.jt.test.demo1.domain.Computer;
import com.jt.test.demo1.domain.entity.Brand;
import com.jt.test.demo1.domain.bo.BrandBO;
import com.jt.test.demo1.domain.vo.BrandVO;
import com.jt.test.demo1.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //先拿出来所有的数据，然后分页
        IPage<Brand> page = new Page<>(bo.getPageNum(),bo.getPageSize());
        LambdaQueryWrapper<Brand> lambda = new QueryWrapper<Brand>().lambda()
//                .eq(Brand::getName,bo.getName())
                .eq(Brand::getFirstLetter,bo.getFirstLetter())
                .between(Brand::getSort,0,200);
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
        new Computer("cpu","ram");
        new Brand();
        IPage<Brand> result = brandService.page(page,lambda);
        List<Brand> records = result.getRecords();
        List<BrandVO> voList = convert.entities2vos(records);
        return HttpResult.success(result.getTotal(),voList);
    }
    /**
     *
     */
}
