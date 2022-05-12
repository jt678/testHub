package com.jt.test.helper;
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
}
