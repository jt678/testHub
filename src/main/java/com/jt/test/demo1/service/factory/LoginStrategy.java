package com.jt.test.demo1.service.factory;

/**
 * LoginStrategy
 *
 * @Author: jt
 * @Date: 2022/12/15 16:43
 */
public interface LoginStrategy {
    /**
     * 进行登录处理
     * @return
     */
    String login(String username, String password);

    /**
     * 该登录类型
     * @return
     */
    String getType();

}
