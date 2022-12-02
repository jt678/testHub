package com.jt.test.demo1.utils;

import java.math.BigDecimal;
import java.util.UUID;


/**
 * 数据库主键生成规则
 */
public class SqlIdUtils {

    private static SnowFlakeIdsHelper snowFlakeIdsHelper = null;

    private static Object lock_obj = new Object();

    private static SnowFlakeIdsHelper getSnowFlakeIdsHelper() {
        if (snowFlakeIdsHelper == null) {
            initSnowFlakeIdsHelper();
        }
        return snowFlakeIdsHelper;
    }

    private static void initSnowFlakeIdsHelper() {
        synchronized (lock_obj) {
            if (snowFlakeIdsHelper == null) {
                snowFlakeIdsHelper = SpringUtils.getBean(SnowFlakeIdsHelper.class);
            }

        }
    }

    /**
     * uuid规则
     *
     * @return
     */
    public static String getUuid() {
        SnowFlakeIdsHelper h = getSnowFlakeIdsHelper();
        if (h != null) {
            return snowFlakeIdsHelper.nextId() + "";
        }
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获得主键ID
     *
     * @return
     */
    public static long getId() {
        SnowFlakeIdsHelper h = getSnowFlakeIdsHelper();
        if (h != null) {
            return snowFlakeIdsHelper.nextId();
        }
        return 0;
    }

    /**
     * 获得主键ID
     *
     * @return
     */
    public static BigDecimal getBId() {

        SnowFlakeIdsHelper h = getSnowFlakeIdsHelper();
        if (h != null) {
            return BigDecimal.valueOf(snowFlakeIdsHelper.nextId());
        }
        return BigDecimal.ZERO;
    }

}
