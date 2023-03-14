package com.jt.test.demo1.controller;

import com.jt.test.demo1.common.HttpResult;
import com.jt.test.demo1.domain.bo.RoleBO;
import com.jt.test.demo1.domain.entity.JgjAppuser;
import com.jt.test.demo1.domain.entity.Role;
import com.jt.test.demo1.helper.RoleExcelHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

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
    public HttpResult<List<JgjAppuser>> importExcel(@RequestPart("file")MultipartFile file) throws IOException {
        return roleExcelHelper.importExcel(file);
    }

    /**
     * EasyExcel导出郴州2月份所有大队下面的清单
     */
    @ApiOperation("导出郴州2月份所有大队下面的清单")
    @PostMapping("/exportForFeb")
    @ResponseBody
    public HttpResult exportForFeb(HttpServletResponse res) throws IOException, ParseException {
        return roleExcelHelper.exportForFeb(res);
    }
}
