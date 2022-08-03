package com.jt.test.helper;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.test.common.HttpResult;
import com.jt.test.domain.entity.Role;
import com.jt.test.domain.bo.RoleBO;
import com.jt.test.service.RoleService;
import com.jt.test.utils.Excel.CustomCellWriteHeightConfig;
import com.jt.test.utils.Excel.CustomCellWriteWeightConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * RoleExcelHelper
 *
 * @Author: jt
 * @Date: 2022/8/3 14:08
 */
@Service
public class RoleExcelHelper {
    @Autowired
    private RoleService roleService;
    /**
     * 导出功能具体逻辑
     * @param res
     */
    public HttpResult export(HttpServletResponse res, RoleBO bo) throws IOException {
        setExcelRes(res,"用户权限导出");
        //按条件查询的数据（时间，状态）
        List<Role> targetList = roleService.list(new QueryWrapper<Role>().lambda()
                .eq(Role::getName, bo.getName())
                .or()
                .eq(Role::getStatus, bo.getStatus()));

        EasyExcel.write(res.getOutputStream())
                .head(Role.class)
                //行宽高自适应----注册器处理
                .registerWriteHandler(new CustomCellWriteWeightConfig())
                .registerWriteHandler(new CustomCellWriteHeightConfig())
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("用户权限列表1")
                .doWrite(targetList);
        return HttpResult.success((long) targetList.size(),targetList);
    }

    /**
     * 设置Excel下载响应头
     */
    private void setExcelRes(HttpServletResponse response,String fileName)throws UnsupportedEncodingException {
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    response.setCharacterEncoding("utf-8");

    String textName = URLEncoder.encode(fileName, "UTF-8");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + textName + ".xlsx");
    }

    /**
     * easyExcel导入测试
     * @return
     */
    public HttpResult importExcel(@RequestPart("file") MultipartFile file) throws IOException {
        //需要导入的数据
        List<Role> targetList = EasyExcel.read(file.getInputStream())
                .head(Role.class)
                .sheet("sheet1")
                .doReadSync();

        roleService.saveOrUpdateBatch(targetList);
        return HttpResult.success((long) targetList.size(),targetList);
    }
}
