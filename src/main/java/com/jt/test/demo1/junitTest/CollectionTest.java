package com.jt.test.demo1.junitTest;

import com.alibaba.fastjson.JSONObject;
import com.jt.test.TestApplication;
import com.jt.test.demo1.domain.entity.Brand;
import com.jt.test.demo1.domain.entity.UserInfo;
import com.jt.test.demo1.helper.CollectionListSetHelper;
import com.jt.test.demo1.service.BrandService;
import com.jt.test.demo1.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CollectionTest
 * 集合测试
 * @author jt
 * @date 2022/4/8
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class CollectionTest {
    @Autowired
    private BrandService brandService;
    @Autowired
    private CollectionListSetHelper collectionListSetHelper;


    @Test
    public void asListTest() {

        String[] strings = {"1", "2", "3"};

        List<String> list = new ArrayList<>(Arrays.asList(strings));

        System.out.println(list);
        collectionListSetHelper.collectionMentor();
        System.out.println("成功执行方法并启动spring boot");
    }

    @Test
    /**
     * Collections.reverse的使用
     */
    public void listReverse() {
        String[] strings = new String[]{"1", "2", "3", "4"};
        List<String> list = new ArrayList<>(Arrays.asList(strings));
        System.out.println("原数组：" + list + "\n");
        Collections.reverse(list);
        System.out.println("反序输出数组：" + list + "\n");

        //场景-----int数组转成String数组输出，并且倒序
        int[] intArray = new int[]{5, 6, 7, 8};
        //转string数组
        List<String> stringsList = new ArrayList<>();
        for (int i = 0; i < intArray.length; i++) {
            stringsList.add(String.valueOf(intArray[i]));
        }
        //倒序输出
        Collections.reverse(stringsList);
        System.out.println("反序后int数组:" + stringsList);
    }

    /**
     * for循环删除测试
     */
    @Test
    public void forDel() {

        //删除错误，下标不准确
        List<Long> idList = brandService.list().stream().map(Brand::getId).collect(Collectors.toList());
        for (int i = 0; i < idList.size(); i++) {
            //第二次for循环在这里会发现本来应该取下标为1，即第2个元素，结果由于第一次循环删除了一个元素，导致现在直接取到原本的第3个元素，第2个元素就没有删除，以此类推。
            Long deleteTarget = idList.get(i);
            if (deleteTarget > 0) {
                idList.remove(deleteTarget);
            }
        }
        //预期情况是list里的值都大于零应该会把list清空了，结果并没有
        System.out.println("idList:" + JSONObject.toJSONString(idList));


        List<Integer> statusList = brandService.list().stream().map(Brand::getFactoryStatus).collect(Collectors.toList());
        //报错
//        for (Integer status : statusList) {
//            if (! status.equals(3)){
//                statusList.remove(status);
//            }
//        }

        //迭代器删除成功-----使用list.removeIf(status -> !status.equals(3))也能成功，它内部也使用了迭代器来删除
        Iterator<Integer> iterator = statusList.iterator();
        while (iterator.hasNext()) {

            Integer next = iterator.next();
            if (next == null || !next.equals(3)) {

                iterator.remove();
            }
        }
        System.out.println("statusList:" + JSONObject.toJSONString(statusList));
    }

    /**
     * 获取List的交集
     */
    @Test
    public void intersection(){
        //如果不用此方式，直接Arrays.asList会报UnsupportedOperationException
        List<String> listA = new ArrayList<>(Arrays.asList("1st,2nd,3rd,4th".split(",")));
        List<String> listB = new ArrayList<>(Arrays.asList("1st,2nd,4th,5th,6th".split(",")));
        //方法一：通过retainAll直接过滤
        List<String> listIn = new ArrayList<>();
        listIn.addAll(listA);

        listIn.retainAll(listB);
        //A有的，B没有的，取消关联
        listA.removeAll(listIn);
        System.out.println("取消关联"+listA);
        //B有的,A没有的，增加关联
        listB.removeAll(listIn);
        System.out.println("建立关联" + listB);

        System.out.println("第一次过滤："+listA);
        //方法二:通过流的filter过滤掉数据
        List<String> listC = Arrays.asList("1st,2nd,4th".split(","));
        List<String> listD = listC.stream().filter(item -> listA.contains(item)).collect(Collectors.toList());
        System.out.println("第二次stream流过滤："+listD);
    }


    /**
     * 获取List的并集
     */
    @Test
    public void unionSet() {
        List<String> listA = Arrays.asList("1st,2nd,3rd".split(","));
        List<String> listB = Arrays.asList("1st,2nd".split(","));

    }

    /**
     * 获取List的差集
     */
    @Test
    public void diffSet() {
        List<String> listA = Arrays.asList("1st,2nd,3rd".split(","));
        List<String> listB = Arrays.asList("1st,2nd".split(","));
        //预期结果是3rd------成功
        listA.removeAll(listB);
        System.out.println(listA);
    }
    @Autowired
    private UserInfoService service;

    /**
     * tableId注解自动生成雪花id
     */
    @Test
//    @Transactional(rollbackFor = Exception.class)
    public void idTest(){
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setSubscribe(3);
            service.save(userInfo);
            System.out.println("成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * forEachRemaining测试，输出剩余元素
     */
    @Test
    public void forEachRemainingTest(){
        //给list赋值
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);
        }
        Iterator<Integer> setIterator = set.iterator();
        //因为set没有下标，使用外部变量i来控制循环
        int i = 0;
        while (setIterator.hasNext()){
            System.out.println(setIterator.next());
            i++;
            if (i == 5){
                break;
            }
        }
        System.out.println("接下来是forEachRemaining输出");
//        注意转换成流之后取新的iterator会失效
//        set.stream().iterator().forEachRemaining(System.out::println);
        System.out.println("==================================");
        setIterator.forEachRemaining(System.out::println);
    }



}
