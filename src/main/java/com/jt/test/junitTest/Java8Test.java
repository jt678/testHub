package com.jt.test.junitTest;

import com.alibaba.fastjson.JSON;
import com.jt.test.TestApplicationMapTest;
import com.jt.test.domain.Brand;
import com.jt.test.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java8Test
 *
 * @Author: jt
 * @Date: 2022/6/28 14:21
 */
@SpringBootTest(classes = TestApplicationMapTest.class)
@RunWith(SpringRunner.class)
public class Java8Test {
    @Autowired
    private BrandService brandService;


    /**
     * Comparator的comparing相关方法
     * .comparing()
     * .reversed()
     * .nullsFirst()----null元素放最前
     * .thenComparing()----继续进行下一层比较
     * .comparingInt/Double/Long
     */
    @Test
    public  void comparatorTest(){
        List<Brand> brandList1 = brandService.list();
        List<Brand> brandList2 = brandService.list();
        //将List转成流
        Stream<Brand> stream = brandList1.stream();
        //把流里的name属性重新拿出再生成一个集合
        List<String> nameList = stream.map(item -> item.getName()).collect(Collectors.toList());
//        Lists.newArrayList();
//        Maps.newHashMap();
        Comparator<Brand> comparing1 = Comparator.comparing(Brand::getProductCount);
        Comparator<Brand> comparing2 = Comparator.comparing(Brand::getName);
        //按照ProductCount来排序，假如有相同的ProductCount再按照name来排列在后面接 reversed()方法反转排序
        Comparator<Brand> thenComparing = Comparator.comparing(Brand::getProductCount).thenComparing(Brand::getName);
        Collections.sort(brandList1,comparing1);
        Collections.sort(brandList2,comparing2);
        Collections.sort(brandList1,thenComparing);
        System.out.println(JSON.toJSONString(brandList1));
        System.out.println("==============================");
        System.out.println(JSON.toJSONString(brandList2));
//        brandList.forEach(System.out::println);
        //根据ProductCount对Brand集合进行去重
        ArrayList<Brand> onlyBrandList = brandList1.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Brand::getProductCount))), ArrayList::new));
    }
}
