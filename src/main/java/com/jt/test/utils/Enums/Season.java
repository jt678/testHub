package com.jt.test.utils.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Season
 *
 * @Author: jt
 * @Date: 2022/8/8 15:11
 */
@AllArgsConstructor
@Getter
public enum Season {

    SPRING(1,"春天","温暖"),
    SUMMER(2,"夏天","炎热"),
    AUTUM(3,"秋天","凉爽"),
    WINTER(4,"冬天","寒冷");

    private int order;
    private String season;
    private String description;
}
