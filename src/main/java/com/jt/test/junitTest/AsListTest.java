package com.jt.test.junitTest;
import com.jt.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * AsListTest
 *
 * @author jt
 * @date 2022/4/8
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class )
public class AsListTest {
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

}
