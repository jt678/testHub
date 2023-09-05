package com.jt.test.demo1.helper;

import com.jt.test.demo1.domain.Person;
import com.jt.test.demo1.domain.vo.User;
import com.jt.test.demo1.utils.Enums.GenderEnum;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CollectionListSetTest
 *
 * @author jt
 * @date 2022/4/12
 **/
@Service
public class CollectionListSetHelper {


    public  void collectionMentor(){
        Collection<Person> personCollection = new ArrayList<>();
        //List和Collection的区别，理解：Collection是接口，包含List和Set两种实现，List下又包含了ArrayList,LinkedList,TreeList等
//      List<Person> personCollection = new ArrayList<>();
        //女性：0、男性：1
        Person person1 = new Person(1L,"测试1",GenderEnum.FEMALE.getGender(),21);
        Person person2 = new Person(2L,"测试2",GenderEnum.FEMALE.getGender(),22);
        Person person3 = new Person(3L,"测试3",GenderEnum.MALE.getGender(), 23);
        Person person4 = new Person(4L,"测试4",GenderEnum.MALE.getGender(), 24);

        personCollection.add(person1);
        personCollection.add(person2);
        personCollection.add(person3);
        personCollection.add(person4);

        Iterator<Person> personIterator = personCollection.iterator();
        while (personIterator.hasNext()){
            Person nextPerson = personIterator.next();
            if(nextPerson.getId()< 3){
            System.out.println(nextPerson);
            System.out.println("第一组姓名："+ nextPerson.getName()+"   性别："+ nextPerson.getSex());}
            else{
                System.out.println(nextPerson);
                System.out.println("第二组姓名："+ nextPerson.getName()+"   性别："+ nextPerson.getSex()+"\n");
            }
        }
    }

    /**
     * List根据某个字段去重方法
     */

    public void listMath(){
        List<User> userList = new ArrayList<>();

        userList.add(new User(1L,"李大锤",23,"南京"));
        userList.add(new User(2L,"张无忌",18,"西安"));
        userList.add(new User(3L,"刘德华",26,"苏州"));
        userList.add(new User(4L,"郭靖",33,"上海"));

        userList.add(new User(1L,"李大锤",23,"南京"));    //id相同，其他数据也相同
        userList.add(new User(3L,"带头大哥",36,"杭州"));  //id相同，其他数据不同

        System.out.println(userList);
        //根据userid去重
        userList = userList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User :: getId))), ArrayList::new));
        System.out.println(userList);
    }
}
