package com.jt.test.junitTest;

import com.jt.test.TestApplication;
import com.jt.test.domain.Animal;
import com.jt.test.domain.Person;
import com.jt.test.domain.Cat;
import com.jt.test.domain.Dog;
import com.jt.test.domain.Duck;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * PersonFeedTest
 *
 * @author jt
 * @date 2022/5/13
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class PersonFeedTest {
    @Test
    public void personFeed(){
        Animal dog = new Dog("狗子");
        Animal cat = new Cat("猫子");
        Animal duck = new Duck("鸭子");
        Person person = new Person(2L,"jt",1,23);

        person.feed(dog);
        person.feed(cat);
        person.feed(duck);
    }
}
