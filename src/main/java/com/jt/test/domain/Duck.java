package com.jt.test.domain;

import com.jt.test.common.Animal;
import lombok.Data;

/**
 * Duck
 *
 * @author jt
 * @date 2022/5/13
 **/
@Data
public class Duck  extends Animal {
//    注意这个name为什么可以直接用？因为继承了animal的属性name，this.name直接调用属性,this可省略

    public Duck(String Duckname) {
        name = Duckname;
    }
    /**
     * 重写鸭子的afterEat方法
     */
    @Override
    public void afterEat() {
        System.out.println(name+"吃饱了：嘎嘎嘎"+"\n");
    }
}
