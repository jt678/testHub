package com.jt.test.demo1.helper;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.DES;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.test.demo1.common.HttpResult;
import com.jt.test.demo1.domain.entity.JgjAppuser;
import com.jt.test.demo1.domain.entity.JgjWaybill;
import com.jt.test.demo1.domain.entity.Role;
import com.jt.test.demo1.domain.bo.RoleBO;
import com.jt.test.demo1.service.JgjAppuserService;
import com.jt.test.demo1.service.JgjWaybillService;
import com.jt.test.demo1.service.RoleService;
import com.jt.test.demo1.utils.Excel.CustomCellWriteHeightConfig;
import com.jt.test.demo1.utils.Excel.CustomCellWriteWeightConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private JgjAppuserService jgjAppuserService;
    @Autowired
    private JgjWaybillService jgjWaybillService;

    //密码加密字符串
    private String key = "12345678";
    /**
     * 导出功能具体逻辑
     * @param res
     */
    public HttpResult export(HttpServletResponse res, RoleBO bo) throws IOException {
        setExcelRes(res,"用户权限导出");
        //按条件查询的数据（时间，状态）
        List<JgjAppuser> targetList = jgjAppuserService.list();

        EasyExcel.write(res.getOutputStream())
                .head(JgjAppuser.class)
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
    @Transactional(rollbackFor = Exception.class)
    public HttpResult<List<JgjAppuser>> importExcel(@RequestPart("file") MultipartFile file) throws IOException {
        //需要导入的数据
        List<JgjAppuser> targetList = EasyExcel.read(file.getInputStream())
                .head(JgjAppuser.class)
                .sheet("sheet1")
                .doReadSync();
        for (JgjAppuser jgjAppuser : targetList) {
            String password = jgjAppuser.getPassword();
            // key：DES模式下，key必须为8位
            DES des = new DES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
            String secret = des.encryptBase64(password);
            jgjAppuser.setPassword(secret);
        }
        jgjAppuserService.saveBatch(targetList);
//        roleService.saveOrUpdateBatch(targetList);
        return HttpResult.success((long) targetList.size(),targetList);
    }

    public HttpResult exportForFeb(HttpServletResponse res) throws IOException, ParseException {
        setExcelRes(res,"郴州2月份已检查清单");
        //'1554673844686192642','1554675686430244865','1554678062599929858','1554702420085080065'
        //获取郴州下所有大队
        List<String> checkPointList = new ArrayList<>();
        checkPointList.add("1554673844686192642");
        checkPointList.add("1554675686430244865");
        checkPointList.add("1554678062599929858");
        checkPointList.add("1554702420085080065");

        //查出所有大队下的用户
        List<JgjAppuser> appuserList = jgjAppuserService.list(new LambdaQueryWrapper<JgjAppuser>().in(JgjAppuser::getCheckId, checkPointList));
//        List<String> checkIdList = new ArrayList<>();
        List<String> appUserIdList = appuserList.stream().map(JgjAppuser::getUserid).collect(Collectors.toList());
        String beginTimeStr = "2023-02-01 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginTime = sdf.parse(beginTimeStr);


        Date endTime = new Date();

        List<JgjWaybill> waybillList = jgjWaybillService.list(new LambdaQueryWrapper<JgjWaybill>()
                .in(JgjWaybill::getCheckId, appUserIdList)
                .between(JgjWaybill::getGmtCreate,beginTime,endTime)
        );
        EasyExcel.write(res.getOutputStream())
                .head(JgjWaybill.class)
                .registerWriteHandler(new CustomCellWriteWeightConfig())
                .registerWriteHandler(new CustomCellWriteHeightConfig())
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("sheet1")
                .doWrite(waybillList);
        return HttpResult.success((long)waybillList.size(),waybillList);

    }
}
