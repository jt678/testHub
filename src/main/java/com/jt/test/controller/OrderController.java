package com.jt.test.controller;
import com.jt.test.common.HttpResult;
import com.jt.test.domain.bo.OrderBO;
import com.jt.test.domain.vo.OrderVO;
import com.jt.test.helper.OrderHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * OrderController
 *
 * @author jt
 * @date 2022/3/8
 **/
@Controller
@RequestMapping("/test")
@Api("测试")
public class OrderController {
    @Autowired
    private OrderHelper helper;

    @GetMapping("/{id}")
    @ResponseBody
    public OrderVO test(@PathVariable("id")@ApiParam("订单id")Long id){

        return helper.test(id);
    }

    @PostMapping("/list")
    @ResponseBody
    public HttpResult<List<OrderVO>>  list(OrderBO bo){

       return helper.list(bo);
    }

    /**
     * 导出测试
     * @param response
     * @throws Exception
     */
    @ApiOperation("导出测试")
    @GetMapping("/export")
    @ResponseBody
    public void exportExcel( HttpServletRequest request ,HttpServletResponse response){
        helper.exportExcel(response);
    }

    @ApiOperation("导入存储测试")
    @GetMapping("/import")
    @ResponseBody
    public void testWriteExcel( HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("进入导入方法");
        helper.testWriteExcel(request,response);
    }

    @ApiOperation("测试导入1")
    @GetMapping("/easyExcel1")
    @ResponseBody
    public String EasyExcel(HttpServletRequest request,HttpServletResponse response,@RequestParam("file")MultipartFile file) throws IOException {
        helper.EasyExcel(response,file);
        return "Sucess";
    }

    @ApiOperation("测试导入3")
    @PostMapping("/easyExcel3")
    @ResponseBody
    public boolean importExcel(@RequestParam("file")MultipartFile file){
        return helper.importExcel(file);
    }

}
