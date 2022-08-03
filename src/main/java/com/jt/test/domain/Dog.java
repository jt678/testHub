package com.jt.test.domain;

import lombok.Data;

/**
 * Dog
 *
 * @author jt
 * @date 2022/5/13
 **/
@Data
public class Dog extends Animal {
//    注意这个name为什么可以直接用？因为继承了animal的属性name，this.name直接调用属性,this可省略

    public Dog(String DogName) {
        name = DogName;
    }

    /**
     * 重写狗的eat方法
     */
    @Override
    public void afterEat() {
        System.out.println(name+"吃饱了：汪汪--"+"\n");
    }
}
