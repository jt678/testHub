package com.jt.test.helper;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.test.common.HttpResult;
import com.jt.test.convert.OrderConvert;
import com.jt.test.domain.MemberPrice;
import com.jt.test.domain.Order;
import com.jt.test.domain.bo.OrderBO;
import com.jt.test.domain.dto.OrderDTO;
import com.jt.test.domain.vo.OrderVO;
import com.jt.test.listener.MemberPriceListener;
import com.jt.test.mapper.OrderMapper;

import com.jt.test.service.MemberPriceService;
import com.jt.test.service.OrderService;
import com.jt.test.utils.SqlIdUtils;
import com.jt.test.utils.TestFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderHelper
 *
 * @author jt
 * @date 2022/3/8
 **/
@Service
@Slf4j
public class OrderHelper {

    @Autowired
    OrderService service;
    @Autowired
    OrderConvert convert;
    @Autowired
    MemberPriceService memberPriceService;

    public OrderVO test(Long id) {

        Order order = service.getById(id);
        OrderVO orderVO = convert.entityToVO(order);
        return orderVO;
    }

    /**
     * 导入测试存储
     */
    public void testWriteExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        // 设置单个文件大小为3M 2的10次幂=1024
        fileUpload.setFileSizeMax((long) (3 * Math.pow(2, 20)));
        // 总文件大小为30M
        fileUpload.setSizeMax((long) (30 * Math.pow(2, 20)));
        List<FileItem> list = fileUpload.parseRequest((RequestContext) request);
        for (FileItem fileItem : list) {
            // 判断是否为附件
            if (!fileItem.isFormField()) {
                // 是附件
                InputStream inputStream = fileItem.getInputStream();
                List<MemberPrice> memberPriceList = new ArrayList<>();
                EasyExcel.read(inputStream, MemberPrice.class,
                        new AnalysisEventListener<MemberPrice>() {
                            // 每解析一行数据,该方法会被调用一次
                            @Override
                            public void invoke(MemberPrice data,
                                               AnalysisContext analysisContext) {
                                System.out.println("解析数据为:" + data.toString());
                                MemberPrice memberPrice = new MemberPrice();
                                memberPrice.setId(SqlIdUtils.getId());
                                memberPrice.setProductId(data.getProductId());
                                memberPrice.setMemberLevelId(data.getMemberLevelId());
                                memberPrice.setMemberPrice(data.getMemberPrice());
                                memberPrice.setMemberLevelName(data.getMemberLevelName());
                                memberPriceList.add(memberPrice);
                            }

                            // 全部解析完成被调用
                            @Override
                            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                                System.out.println("解析完成...");
                                // 可以将解析的数据保存到数据库
                                memberPriceService.saveBatch(memberPriceList);
                            }
                        }).sheet().doRead();
            }
        }

    }

    /**
     * 导出测试
     */
    public void exportExcel(HttpServletResponse response) {
        List<MemberPrice> memberPriceList = memberPriceService.list();
        try {

            String title = "测试导出2";
            String charSet = StandardCharsets.UTF_8.name();
            response.setCharacterEncoding(charSet);

            String name = URLEncoder.encode(title, charSet);
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + name + ".xlsx");

            EasyExcel.write(response.getOutputStream(), MemberPrice.class)
                    .sheet("sheet 1")
                    .doWrite(memberPriceList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String EasyExcel(HttpServletResponse response, MultipartFile file) throws IOException {

        //先创建好List，每次invoke调用放入List中，最后一次saveBatch
        List<MemberPrice> memberPriceList = new ArrayList<>();

        EasyExcel.read(file.getInputStream(), MemberPrice.class,
                new AnalysisEventListener<MemberPrice>() {


                    @Override
                    public void invoke(MemberPrice memberPrice, AnalysisContext analysisContext) {
                        memberPriceList.add(memberPrice);
                        log.info("读取到一条数据{}", JSON.toJSONString(memberPrice));
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("数据解析完成");
                    }
                }).sheet().doRead();


        return "test";

    }


    //            EasyExcel.read(file.getInputStream(), MemberPrice.class, new PageReadListener<MemberPrice>(dataList -> {
//                for (MemberPrice memberPrice : dataList) {
//                    log.info("读取到一条数据{}", JSON.toJSONString(memberPrice));
//                }
//            })).sheet().doRead();


//        Order order = new Order();
//        order.setNote("3");
//        StringBuilder prefix = new StringBuilder();
//        prefix.append("2");
//        OrderVO orderVO = new OrderVO();
//        BeanUtils.copyProperties(prefix,orderVO.getNote());
//        return orderVO;


    /**
     * 导入最终测试
     *
     * @param file
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean importExcel(MultipartFile file) {
        try {
//            List<Object> objectList  = ExcelUtil.readMoreThan1000RowBySheetFromInputStream(file.getInputStream(),null,MemberPrice.class);
            List<Object> objectList = new ArrayList<>();
            List<MemberPrice> memberPriceList = new ArrayList<>();
            for (Object o : objectList) {

                System.out.println(o);
                MemberPrice memberPrice = new MemberPrice();
                List<String> strList = (List<String>) o;
                memberPrice.setId(Long.valueOf(strList.get(0) != null ? strList.get(0) : ""));
                memberPrice.setProductId(Long.valueOf(strList.get(1) != null ? strList.get(1) : ""));
                memberPrice.setMemberLevelId(Long.valueOf(strList.get(2) != null ? strList.get(2) : ""));
                memberPrice.setMemberPrice(new BigDecimal(strList.get(3) != null ? strList.get(3) : ""));
                memberPrice.setMemberLevelName(strList.get(4) != null ? strList.get(4).toString() : "");
                memberPriceList.add(memberPrice);
            }
            return memberPriceService.saveBatch(memberPriceList);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 条件查询
     *
     * @return
     */
    public HttpResult<List<OrderVO>>  list(OrderBO bo) {
        List<OrderVO> voList = new ArrayList<>();
        OrderDTO orderDTO = convert.boToDto(bo);

        //查entity数据
        IPage<Order> page = new Page<>(bo.getPageNum(), bo.getPageSize());
        IPage<Order> result = service.listBySearch(page, orderDTO);

        List<Order> orderList = result.getRecords();
//        List<Order> orderList = service.listBySearch();
        voList = convert.entityListToVoList(orderList);
        return HttpResult.success(result.getTotal(),voList);
    }
}
