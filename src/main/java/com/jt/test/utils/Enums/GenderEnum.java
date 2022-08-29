package com.jt.test.utils.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderEnum {
    FEMALE(1,"女性",0),
    MALE(2,"男性",1)

    ;
    private int id;
    private String remark;
    private int gender;


}
