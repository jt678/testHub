package com.jt.test.controller;

import com.jt.test.common.HttpResult;
import com.jt.test.domain.bo.BrandBO;
import com.jt.test.domain.vo.BrandVO;
import com.jt.test.helper.BrandHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * BrandController
 *
 * @author jt
 * @date 2022/5/18
 **/
@Controller
@Api(tags = "品牌brand接口")
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandHelper brandHelper;

    @ApiOperation("品牌分页查询")
    @PostMapping("/list")
    @ResponseBody
    public HttpResult<List<BrandVO>> list(BrandBO bo){
        return brandHelper.list(bo);
    }

}
