package com.jt.test.common;

/**
 * 无返回值，有多个参数
 */
@FunctionalInterface
public interface NoReturnMultiParam {
    void method(int a, int b ,String c);
}
