package com.jt.test.convert;

import com.jt.test.domain.Brand;
import com.jt.test.domain.bo.BrandBO;
import com.jt.test.domain.dto.BrandDTO;
import com.jt.test.domain.vo.BrandVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-19T10:39:58+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_312 (International Business Machines Corporation)"
)
@Component
public class BrandConvertImpl implements BrandConvert {

    @Override
    public List<BrandVO> entities2vos(List<Brand> records) {
        if ( records == null ) {
            return null;
        }

        List<BrandVO> list = new ArrayList<BrandVO>( records.size() );
        for ( Brand brand : records ) {
            list.add( brandToBrandVO( brand ) );
        }

        return list;
    }

    @Override
    public BrandDTO bo2Dto(BrandBO bo) {
        if ( bo == null ) {
            return null;
        }

        BrandDTO brandDTO = new BrandDTO();

        brandDTO.setPageSize( bo.getPageSize() );
        brandDTO.setPageNum( bo.getPageNum() );
        brandDTO.setName( bo.getName() );
        brandDTO.setFirstLetter( bo.getFirstLetter() );
        brandDTO.setLogo( bo.getLogo() );

        brandDTO.setCondition( cn.hutool.core.util.StrUtil.isNotEmpty(bo.getCondition())?java.util.Arrays.asList(bo.getCondition().split(",")):null );

        return brandDTO;
    }

    protected BrandVO brandToBrandVO(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        BrandVO brandVO = new BrandVO();

        brandVO.setId( brand.getId() );
        brandVO.setName( brand.getName() );
        brandVO.setFirstLetter( brand.getFirstLetter() );
        brandVO.setSort( brand.getSort() );
        brandVO.setFactoryStatus( brand.getFactoryStatus() );
        brandVO.setShowStatus( brand.getShowStatus() );
        brandVO.setProductCount( brand.getProductCount() );
        brandVO.setProductCommentCount( brand.getProductCommentCount() );
        brandVO.setLogo( brand.getLogo() );
        brandVO.setBigPic( brand.getBigPic() );
        brandVO.setBrandStory( brand.getBrandStory() );

        return brandVO;
    }
}
