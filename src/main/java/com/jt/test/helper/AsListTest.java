package com.jt.test.helper;

import com.alibaba.druid.sql.SQLUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import com.jt.test.domain.Order;
import com.jt.test.domain.bo.StringBO;
import com.jt.test.utils.SqlIdUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.management.QueryEval;
import java.util.Arrays;
import java.util.List;

/**
 * AsListTest
 *
 * @author jt
 * @date 2022/4/8
 **/
public class AsListTest {
    public static void main(String[] args) {

//        String[] strings = {"1","2","3"};
//        if(
//        Arrays.stream(strings).iterator().hasNext()){
//
//        }
//        List list = Arrays.asList();
//        list.set(1,"A");
//        list.set(2,"B");
//        System.out.println(list);
        CollectionListSetTest.collectionMentor();
        System.out.println("成功执行方法并启动spring boot");
    }
}
