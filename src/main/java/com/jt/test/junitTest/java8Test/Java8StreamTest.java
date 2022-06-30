package com.jt.test.junitTest.java8Test;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.test.TestApplicationMapTest;
import com.jt.test.domain.Brand;
import com.jt.test.service.BrandService;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java8StreamTest
 *
 * @Author: jt
 * @Date: 2022/6/29 14:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationMapTest.class)
public class Java8StreamTest {
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

    /**
     * 流的基本操作--（中间）filter,map,limit/(终端)collect
     *  中间还有：sorted,distinct,skip等
     *  终端：forEach,count等
     */
    @Test
    public void StreamBasic(){
        //先得到数据转换成流
        Stream<Brand> brandStream = brandService.list().stream();
        //过滤状态为1的(filter)
        Stream<Brand> filterBrandStream = brandStream.filter(brand -> brand.getShowStatus().equals(1));
        //取品牌名字的集合(map映射方法)，跳过第一条(skip)且只取三条(limit),再把流转成List(collect)
        List<String> brandNameList = filterBrandStream.map(brand -> brand.getName()).skip(1).limit(3).collect(Collectors.toList());

        System.out.println(JSON.toJSONString(brandNameList));

        //List中元素字母去重输出
        List<String> words = Arrays.asList("Hello", "World");
        List<String> wordArr = words.stream()
                .map(word->word.split(""))
                //把两个array（h e l l o ++++ w o r l d）整合到一个流
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(wordArr);//打印每个元素的长度

        //数据量少，串行流的情况下findAny返回的是第一个元素，在并行流parallelStream中会随机取
        //注意findAny，findFirst可能会什么都没找到直接get会报空指针异常，Optional容器类的get（）会抛一个不存在此元素异常，可以用它的isPresent()判断是否包含值或者orElse()在没有返回值时设置一个人默认值
        Optional<String> anyOption = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .findAny();
        //并行流parallelStream中会随机取
        String any2 = words.parallelStream()
                .map(w -> w.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .findAny()
                .get();
        //打印操作容器
        String any = anyOption.get();
        System.out.println(anyOption);
        System.out.println(any);
        System.out.println(any2);

        //concat合并流，match方法，数据流只能被消费一次特性
        ArrayList<Object> list1 = Lists.newArrayList();
        list1.add("this is list1 and");
        ArrayList<Object> list2 = Lists.newArrayList();
        list2.add("list2");
        list2.add("list2Demo");

//        Stream<Object> stream = list2.stream();
        List<Object> concatList = Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(concatList));
        //与allMatch对应还有noneMatch，此处如果定义了list2流则它只能被消费一次，因为此处流被上面collect操作结束了，所以此时stream里没有内容
        boolean b1 = list2.stream().allMatch(e -> e.equals("list2"));
        System.out.println(b1);
        //同理上面流被allMatch消费了，anyMatch也不能生效
        boolean b2 = list2.stream().anyMatch(e -> e.equals("list2"));
        System.out.println(b2);
    }

    @Test
    public void StreamMath(){
        //使用reduce求和------reduce除了减少还有归纳的意思
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        //0是初始值，否则.get()获取
        Integer sumNum = nums.stream().reduce(0,(a, b) -> a + b);
        System.out.println(sumNum);

        //ifPresent()使用以及返回最大值和最小值
        nums.stream().reduce(Integer::max).ifPresent(x -> System.out.println(x));
        nums.stream().reduce(Integer::min).ifPresent(x -> System.out.println(x));
    }
}
