//package com.jt.test.demo1.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//
///**
// * RedisConfig
// * redis连接配置------这里如果配置了，yml里面再配置，优先读取这里的，yml失效
// * 原因:yml也是通过引入的spring-data-redis包下通过自动装配来实现的，如果再设置一次会覆盖之前的设置
// * @author jt
// * @date 2022/5/5
// **/
//// configuration,service,controller,component,repository这些注解spring都默认了单例
//@Configuration
//public class RedisConfig {
//    @Value("spring.redis.database")
//    private int database;
//    @Value("spring.redis.host")
//    private String host;
//
//    @Bean
//    public RedisConnectionFactory redisCF(){
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//        configuration.setHostName(host);
//        configuration.setPort(6379);
//        configuration.setDatabase(database);
//        return new JedisConnectionFactory(configuration);
//    }
//}
