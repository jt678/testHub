package com.jt.test.demo1.junitTest.java8Test;

import com.jt.test.TestApplication;
import com.jt.test.demo1.domain.entity.UserInfo;
import com.jt.test.demo1.domain.vo.User;
import com.jt.test.demo1.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Java8OptionalTest
 * optional方法的使用和用处
 * @Author: jt
 * @Date: 2023/3/20 13:56
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class Java8OptionalTest {

    @Autowired
    private UserInfoService userInfoService;


    @Test
    public void test1() {
        User user = new User("23",1);
        log.debug("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        log.info(result.toString());
        log.debug("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
        log.info(result2.toString());
    }
    private User createNewUser() {
        //如果user不为空，orElse()方法还是会走进此方法，并new一个新user，但是因为user是有值的，所以返回的result还是原user("23"，1)，new的user没有用到
        //orElseGet()如果user值不是空则不会走此方法，result直接返回user，不会new一个新的user
        log.debug("Creating New User");
        return new User("123", 1);
    }

    @Test
    public void map() {
        // 如果Optional实例值非null，执行map(lambda表达式，任意类型)，返回新的Optional实例
        Optional<String> notNullOptional = Optional.of("Test Content");
        Optional<String> nullOptional = Optional.empty();

        Optional<String> notNullToUpperCase = notNullOptional.map((value) -> value.toUpperCase());


        System.out.println("【map()】" + notNullToUpperCase.orElse("No value found"));

        Optional<Integer> notNullToInteger = notNullOptional.map((value) -> 1);
        // 输出：1
        System.out.println("【map()】" + notNullToInteger.orElse(2));

        // 如果Optional实例值为null，不用执行map()，返回空Optional
        Optional<String> emptyToUpperCase = nullOptional.map((value) -> value.toUpperCase());
        // 输出：No value found
        System.out.println("【map()】" + emptyToUpperCase.orElse("No value found"));
    }

    @Test
    public void map2() {
//        User user = new User("xxx@qq.com", 21);
        User user = new User(null, 21);
        String email = Optional.ofNullable(user)
                .map(u -> u.getName()).orElse("default@gmail.com");
        log.info(email);
//        assertEquals(email, user.getName());
    }
    @Test
    public void flatMap() {
        User user = new User("xxx@qq.com", 23);
////        user.setPosition("Developer");
////         String position = Optional.ofNullable(user)
////                .flatMap(u -> u.getPosition()).orElse("default");
//        log.info(position);
//        assertEquals(position, user.getPosition().get());
    }






}
