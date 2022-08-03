package com.jt.test.helper;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.test.common.HttpResult;
import com.jt.test.convert.OrderConvert;
import com.jt.test.domain.entity.MemberPrice;
import com.jt.test.domain.entity.Order;
import com.jt.test.domain.bo.OrderBO;
import com.jt.test.domain.dto.OrderDTO;
import com.jt.test.domain.vo.OrderVO;

import com.jt.test.service.MemberPriceService;
import com.jt.test.service.OrderService;
import com.jt.test.utils.SqlIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
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
