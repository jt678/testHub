package com.jt.test.domain.entity;

import lombok.Data;

/**
 * Person
 *
 * @author jt
 * @date 2022/4/12
 **/
@Data
public class Person {
    private Long id;
    private String name;
    private Integer sex;
    private Integer Age;

    public Person(Long id, String name, Integer sex, Integer age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        Age = age;
    }
}
