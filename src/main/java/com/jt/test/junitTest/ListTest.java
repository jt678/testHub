package com.jt.test.junitTest;
import com.alibaba.fastjson.JSONObject;
import com.jt.test.TestApplication;
import com.jt.test.domain.Brand;
import com.jt.test.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AsListTest
 *
 * @author jt
 * @date 2022/4/8
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class )
public class ListTest {
    @Autowired
    private BrandService brandService;

    @Test
    public  void asListTest() {

        String[] strings = {"1","2","3"};
        if(
        Arrays.stream(strings).iterator().hasNext()){

        }
        List list = Arrays.asList();
        list.set(1,"A");
        list.set(2,"B");
        System.out.println(list);
        CollectionListSetTest.collectionMentor();
        System.out.println("成功执行方法并启动spring boot");
    }

    @Test
    /**
     * Collections.reverse的使用
     */
    public void listReverse(){
        List list = new ArrayList();
        String[] strings = new String[]{"1","2","3","4"};
        list.addAll(Arrays.asList(strings));
        System.out.println("原数组："+list+"\n");
        Collections.reverse(list);
        System.out.println("反序输出数组："+list+"\n");

        //场景-----int数组转成String数组输出，并且倒序
        int[] intArray = new int[]{5,6,7,8};
        //转string数组
        List<String> stringsList = new ArrayList<>();
        for (int i = 0;i<intArray.length;i++){
          stringsList.add(String.valueOf(intArray[i]) );
        }
        //倒序输出
        Collections.reverse(stringsList);
        System.out.println("反序后int数组:"+stringsList);
    }

    /**
     * for循环删除测试
     */
    @Test
    public void forDel(){

        //删除错误，下标不准确
        List<Long> idList = brandService.list().stream().map(Brand::getId).collect(Collectors.toList());
        for (int i = 0; i < idList.size(); i++) {
           if (idList.get(i)>0){
               idList.remove(i);
           }
        }
        System.out.println("idList:"+JSONObject.toJSONString(idList));

        //报错
        List<Integer> statusList = brandService.list().stream().map(Brand::getFactoryStatus).collect(Collectors.toList());
//        for (Integer status : statusList) {
//            if (! status.equals(3)){
//                statusList.remove(status);
//            }
//        }
//        System.out.println("statusList"+JSONObject.toJSONString(statusList));

        //迭代器删除成功
        Iterator<Integer> iterator = statusList.iterator();
        while (iterator.hasNext()){

            Integer next = iterator.next();
            if (next == null || ! next.equals(3)){

               iterator.remove();
            }
        }
        System.out.println("statusList:"+JSONObject.toJSONString(statusList));
    }



}
