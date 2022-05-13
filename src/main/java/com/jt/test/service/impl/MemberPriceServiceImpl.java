package com.jt.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.test.domain.entity.MemberPrice;
import com.jt.test.service.MemberPriceService;
import com.jt.test.mapper.MemberPriceMapper;
import org.springframework.stereotype.Service;

/**
* @author j
* @description 针对表【pms_member_price(商品会员价格表)】的数据库操作Service实现
* @createDate 2022-04-18 17:54:13
*/
@Service
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceMapper, MemberPrice>
    implements MemberPriceService{

}




