package com.jt.test.domain.entity;

import lombok.Data;

/**
 * User
 *
 * @Author: jt
 * @Date: 2022/6/24 15:06
 */
@Data
public class User {
    private String id;
    private String name;
    private String age;
    private String state;

    public User(String id, String name, String age, String state) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.state = state;
    }
}
