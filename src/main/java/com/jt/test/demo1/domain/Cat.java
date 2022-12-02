package com.jt.test.demo1.domain;

import lombok.Data;

/**
 * Cat
 *
 * @author jt
 * @date 2022/5/13
 **/
@Data
public class Cat extends Animal {
    //    注意这个name为什么可以直接用？因为继承了animal的属性name，this.name直接调用属性,this可省略

    public Cat(String CatName) {
        name = CatName;
    }
    /**
     * 重写猫的afterEat方法
     */
    @Override
    public void afterEat() {
        System.out.println(name+"吃饱了：喵啊"+"\n");
    }
}
