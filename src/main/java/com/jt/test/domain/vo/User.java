package com.jt.test.domain.vo;

import lombok.Data;

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

    public User(Long id, String name, Integer age, String state) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.state = state;
    }


    public User(String name,Integer age){
        this.name = name;
        this.age = age;
    }
}
