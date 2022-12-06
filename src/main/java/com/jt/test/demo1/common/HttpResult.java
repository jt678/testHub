package com.jt.test.demo1.common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * HttpResult
 *
 * @author jt
 * @date 2022/4/28
 **/
@ApiModel("我的网络请求返回对象")
@Slf4j
@Data
public class HttpResult<T> {
    private int code = 200;
    //private static final int FAIL_CODE = 500;
    private String msg;
    /**
     * 数据体
     */
    private Object data;
    /**
     * 分页查询时的总分页数目
     */
    private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public HttpResult() {
        super();
        this.code = 0;
        this.msg = "SUCCESS";
    }

    public HttpResult(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }


    /**
     * 请求成功时的响应结果
     *
     * @return
     */
    public static <T> HttpResult<T> success() {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setCode(httpResult.code);
        httpResult.setMsg("SUCCESS");
        return httpResult;
    }

    /**
     * 请求成功时的响应结果
     *
     * @param msg 自定义响应内容
     * @return
     */
    public static <T> HttpResult<T> success(String msg) {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setMsg(msg);
        httpResult.setCode(httpResult.code);
        return httpResult;
    }

    /**
     * 请求成功时的响应结果
     *
     * @param data 要响应的数据
     * @return
     */
    public static <T> HttpResult<T> success(T data) {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setCode(httpResult.code);
        httpResult.setMsg("SUCCESS");
        httpResult.setData(data);
        return httpResult;
    }

    /**
     * 请求成功时的响应结果
     *
     * @param total 分页数据总数
     * @param data  自定义响应数据
     * @return
     */
    public static <T> HttpResult<T> success(Long total, T data) {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setCode(httpResult.code);
        httpResult.setMsg("SUCCESS");
        httpResult.setData(data);
        httpResult.setTotal(total);
        return httpResult;
    }



    /**
     * 请求失败时的响应内容
     *
     * @return
     */
    public static <T> HttpResult<T> failed() {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setCode(500);
        httpResult.setMsg("SOETHING IS WRONG!");
        return httpResult;
    }

    /**
     * 请求失败时的相应内容
     *
     * @param msg 自定义相应内容
     * @return
     */
    public static <T> HttpResult<T> failed(String msg) {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setCode(500);
        httpResult.setMsg(msg);
        return httpResult;
    }


}