package com.jt.test.junitTest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.test.TestApplicationMapTest;
import com.jt.test.convert.BrandConvert;
import com.jt.test.domain.Brand;
import com.jt.test.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * QueryWrapperTest
 *
 * @author jt
 * @date 2022/5/19
 **/
@SpringBootTest(classes = TestApplicationMapTest.class)
@RunWith(SpringRunner.class)
public class QueryWrapperTest {
    @Autowired
    BrandService service;
    BrandConvert convert;

    /**
     * allEq,in和having,exist的区别
     */
    @Test
    public void queryTest(){
        //allEq构造器
        HashMap<String,String> map = new HashMap<>();
        map.put("first_letter","S");
        map.put("name","七匹狼");

        QueryWrapper<Brand> queryWrapper = new QueryWrapper<Brand>();


        //allEq
        queryWrapper.allEq(map);
        List<Brand> alleList = service.list(queryWrapper);
        for (Brand brand:alleList) {
            System.out.println(brand.getName());
        }

        //in
        LambdaQueryWrapper<Brand> inQuery = new QueryWrapper<Brand>().lambda();
        String[] s = {"W","S","H","G","F","O"};
        inQuery.in(Brand::getFirstLetter,s);
        List<Brand> inList = service.list(inQuery);
        for (Brand brand:inList) {
            System.out.println(brand.getName());
        }

        //exist
        LambdaQueryWrapper<Brand> existQuery = new QueryWrapper<Brand>().lambda();
        List<String> stringList = Arrays.asList(s);
        List<Brand> existList = service.list(existQuery.in(Brand::getFirstLetter, stringList));
        for (Brand brand:existList) {
            System.out.println(brand.getName());
        }


        //having(单独貌似不生效)
        LambdaQueryWrapper<Brand> havingQuery = new QueryWrapper<Brand>().lambda();
        List<Brand> havingList = service.list(havingQuery.having("id>1"));
        for (Brand brand:havingList) {
            System.out.println(brand.getName());
        }

        //mybatis的listmaps方法
        List<Map<String, Object>> maps = service.listMaps();
        for (Map<String, Object> allmap: maps) {

            //每次得到一个map里所有values
            Collection<Object> values = allmap.values();
            System.out.println(values);
        }
    }
}
