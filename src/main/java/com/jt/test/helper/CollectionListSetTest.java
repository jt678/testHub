package com.jt.test.helper;

import com.jt.test.domain.entity.Person;
import com.jt.test.utils.myEnum.GenderEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * CollectionListSetTest
 *
 * @author jt
 * @date 2022/4/12
 **/
@Service
public class CollectionListSetTest {

    public static void collectionMentor(){
        Collection<Person> personCollection = new ArrayList<>();
        //List和Collection的区别，理解：Collection是接口，包含List和Set两种实现，List下又包含了ArrayList,LinkedList,TreeList等
//      List<Person> personCollection = new ArrayList<>();
        //女性：0、男性：1
        Person person1 = new Person(1L,"测试1", GenderEnum.FEMALE.getGender(),21);
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
            System.out.println("姓名："+ nextPerson.getName()+"   性别："+ nextPerson.getSex());}
            else{
                System.out.println(nextPerson);
                System.out.println("姓名："+ nextPerson.getName()+"   性别："+ nextPerson.getSex()+"\n");
            }
        }
    }
}
