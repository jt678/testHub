package com.jt.test.demo1.domain;

import lombok.Data;

/**
 * Animal
 *
 * @author jt
 * @date 2022/5/13
 **/
@Data
public abstract class Animal {
    /**
     * 动物名
     */
    public String name;

    public void afterEat(){
        System.out.println();
    }
}
