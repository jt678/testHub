package com.jt.test.junitTest.java8Test;

import com.alibaba.fastjson.JSON;
import com.jt.test.TestApplication;
import com.jt.test.domain.entity.Brand;
import com.jt.test.domain.vo.User;
import com.jt.test.service.BrandService;
import org.apache.commons.compress.utils.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
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
@SpringBootTest(classes = TestApplication.class)
public class Java8StreamTest {
    private LocalDateTime start;

    /**
     * stream流只能用一次测试
     */
    @Test
    public void streamTest() {
        Stream<String> stream = Stream.of("A,B,C,D".split(","));
        //第一次使用流
        List<String> listA = stream.filter(item -> item.equals("A") || item.equals("B")).collect(Collectors.toList());
        listA.forEach(System.out::println);
        //第二次使用流
        List<String> listC = stream.filter(item -> item.equals("C")).collect(Collectors.toList());
        listC.forEach(System.out::println);
        //结果：stream has already been operated upon or closed
    }

    /**
     * 测试list转流操作之后。List是否有变化
     */
    @Test
    public void streamListTest() {
        List<String> stringList = Arrays.asList("A,B,C,D".split(","));
        //操作stream
        Stream<String> streamAB = stringList.stream().filter(i -> i.equals("A") || i.equals("B"));
        List<String> AList = streamAB.filter(i->i.equals("B")).collect(Collectors.toList());
        //List还是原来的，不改变List只操作了流
        System.out.println(AList);
        System.out.println(stringList);
    }

    /**
     * 单类运行时间切面
     */
    @Before
    public void init() {
        start = LocalDateTime.now();
    }

    @After
    public void destory() {
        LocalDateTime end = LocalDateTime.now();
        Duration between = Duration.between(start, end);

        System.out.println("耗时：" + between.toString().substring(2));
    }


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
    public void comparatorTest() {
        List<Brand> brandList1 = brandService.list();
        List<Brand> brandList2 = brandService.list();
        //将List转成流
        Stream<Brand> stream = brandList1.stream();
        //把流里的name属性重新拿出再生成一个集合
        List<String> nameList = stream.map(Brand::getName).collect(Collectors.toList());
//        Lists.newArrayList();
//        Maps.newHashMap();
        Comparator<Brand> comparing1 = Comparator.comparing(Brand::getProductCount);
        Comparator<Brand> comparing2 = Comparator.comparing(Brand::getName);
        //按照ProductCount来排序，假如有相同的ProductCount再按照name来排列在后面接 reversed()方法反转排序
        Comparator<Brand> thenComparing = Comparator.comparing(Brand::getProductCount).thenComparing(Brand::getName);
        brandList1.sort(comparing1);
        brandList2.sort(comparing2);
        brandList1.sort(thenComparing);
        System.out.println(JSON.toJSONString(brandList1));
        System.out.println("==============================");
        System.out.println(JSON.toJSONString(brandList2));
//        brandList.forEach(System.out::println);
        //根据ProductCount对Brand集合进行去重
        ArrayList<Brand> onlyBrandList = brandList1.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Brand::getProductCount))), ArrayList::new));
        System.out.println(onlyBrandList);
    }

    /**
     * 流的基本操作--（中间）filter,map,limit/(终端)collect
     * 中间还有：sorted,distinct,skip等
     * 终端：forEach,count等
     */
    @Test
    public void StreamBasic() {
        //先得到数据转换成流
        Stream<Brand> brandStream = brandService.list().stream();
        //过滤状态为1的(filter)
        Stream<Brand> filterBrandStream = brandStream.filter(brand -> brand.getShowStatus().equals(1));
        //取品牌名字的集合(map映射方法)，跳过第一条(skip)且只取三条(limit),再把流转成List(collect)
        List<String> brandNameList = filterBrandStream.map(Brand::getName).skip(1).limit(3).collect(Collectors.toList());

        System.out.println(JSON.toJSONString(brandNameList));

        //List中元素字母去重输出
        List<String> words = Arrays.asList("Hello", "World");
        List<String> wordArr = words.stream()
                .map(word -> word.split(""))
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
        //并行流parallelStream中会随机取,因为在并行流中一个流会被分割成多个子流来并行操作
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

    /**
     * stream流一些基本计算
     */
    @Test
    public void StreamMath() {
        //使用reduce求和------reduce除了减少还有归纳的意思
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        //reduce(identity,accumulator,combiner)
        //0---identity保存归并参数的初始值，当stream为空默认也返回此值
        //(a, b) -> a + b----accumulator（累加器）,a是上次计算的值，b是流的下一个元素的值
        //combiner 在下个方法有用
        int sumNum = nums.stream().reduce(0, (a, b) -> a + b);
        //可以用lambda简化sum函数来实现
        Integer sumReduce = nums.stream().reduce(0, Integer::sum);
        System.out.println((sumNum == 36) + "And" + (sumReduce == 36));

        //combiner
//        Arrays.asList(User::getName)

        //返回最大值和最小值以及ifPresent()如果存在则操作
        nums.stream().reduce(Integer::max).ifPresent(System.out::println);
        nums.stream().reduce(Integer::min).ifPresent(System.out::println);

    }

    /**
     * redyce方法的介绍和参数异同
     */
    @Test
    public void streamReduce() {
        List<Integer> ages = Arrays.asList(25, 30, 45, 28, 32);
        System.out.println(ages.parallelStream().reduce(0, (a, b) -> a + b, Integer::sum));
        System.out.println(ages.stream().reduce(0, Integer::sum));
        System.out.println(ages.stream().reduce(0, (a, b) -> a + b));
        //返回的是Optional容器，要再get一次
        System.out.println(ages.parallelStream().reduce((a, b) -> a + b));
        List<User> userList = Arrays.asList(new User("jt", 14), new User("tt", 13), new User("jj", 15));

        //此处的Integer::sum 就是combiner（组合器），累加函数实现运算，但是流中的包含的是User对象，但是函数的参数是数字和user对象，所以编译器无法推断参数user的类型，需要组合器
        Integer result = userList.stream().reduce(42, (a, b) -> a - b.getAge(), Integer::sum);
        //当顺序读流或累加器参数和它的实现的实现类型匹配，则不需要组合器
        userList.stream().reduce(userList.get(0).getAge(), (a, b) -> a - b.getAge(), Integer::sum);
        System.out.println(result);
    }

    /**
     * 使用并行流安全问题及处理--- 慎用（1.线程不安全 2.可能引起竞态条件 3.并发可能消耗更多资源 4.公共线程池裂开。。。）
     */
    public void parallelStreamT() {
        //并行流线程安全问题
        ArrayList<Integer> testList = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            testList.add(i);
        }
        //1000条
        System.out.println("实验数据条数：" + testList.stream().count());
//      此处是第一次尝试加锁对象错误，应该在操作List加 ---List<Integer> synchronizedTestList = Collections.synchronizedList(testList);

        ArrayList<Integer> targetList1 = Lists.newArrayList();
        ArrayList<Integer> targetList2 = Lists.newArrayList();
        ArrayList<Integer> targetList3 = Lists.newArrayList();
        //未上锁
        testList.parallelStream().forEach(targetList1::add);
        //上锁数据添加
        //方法一：包装我们要操作的List使其上锁
        testList.parallelStream().forEach(Collections.synchronizedList(targetList2)::add);
        //方法二：stream的toArray/collect方法
        Object[] objects = testList.parallelStream().toArray();
        for (Object object : objects) {
            int i = Integer.parseInt(object.toString());
            targetList3.add(i);
        }

        //预期1000条但是实际上每次都只有960条左右，synchronized之后也是(错误原因--应该给进行添加操作的List加锁，因为是他在执行任务时把任务拆成并行),成功加锁后数据一致
        //加synchronized会保证同一时刻只能被一个线程使用
        System.out.println("并行流实验对象1数据条数(线程不安全)：" + targetList1.size()
                + "\n" + "并行流实验对象2数据条数(synchronized)：" + targetList2.size()
                + "\n" + "并行流实验对象3数据条数(toArray)：" + targetList3.size());
    }


}
