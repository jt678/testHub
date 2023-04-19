package com.jt.test.demo1.domain.vo;

import lombok.Data;
import org.apache.commons.math3.analysis.function.Add;

import java.util.Optional;

/**
 * User
 *
 * @Author: jt
 * @Date: 2022/6/24 15:06
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String state;
    private Address address;
    private Optional position;
    public User(Long id, String name, Integer age, String state) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.state = state;
    }

    static Optional<User> getAddress(User user){
        return user == null ? Optional.empty() : Optional.of(user);
    }

    public User(String name,Integer age){
        this.name = name;
        this.age = age;
    }

}
