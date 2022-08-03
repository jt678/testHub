package com.jt.test.controller;

import com.jt.test.common.HttpResult;
import com.jt.test.domain.bo.RoleBO;
import com.jt.test.helper.RoleExcelHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * RoleExcelController
 *
 * @Author: jt
 * @Date: 2022/8/3 13:50
 */
@Controller
@RequestMapping("/role")
@Api(tags = "角色权限导入导出接口")
public class RoleExcelController {

    @Autowired
    private RoleExcelHelper roleExcelHelper;

    /**
     * EasyExcel导出
     */
    @ApiOperation("导出功能")
    @PostMapping("/export")
    @ResponseBody
    public HttpResult export(HttpServletResponse res, RoleBO bo) throws IOException {
      return roleExcelHelper.export(res,bo);
    }

    /**
     * EasyExcel导入-------RequestPart用于文件/表单提交请求的方法上
     */
    @ApiOperation("从Excel导入角色权限")
    @PostMapping("/import")
    @ResponseBody
    public HttpResult importExcel(@RequestPart("file")MultipartFile file) throws IOException {
        return roleExcelHelper.importExcel(file);
    }
}
