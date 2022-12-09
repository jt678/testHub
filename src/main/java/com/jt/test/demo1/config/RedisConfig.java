//package com.jt.test.demo1.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//
///**
// * RedisConfig
// *  redis连接配置------这里如果配置了，yml里面再配置，优先读取这里的，yml失效
// * @author jt
// * @date 2022/5/5
// **/
//@Configuration
//public class RedisConfig {
//    @Bean
//    public RedisConnectionFactory redisCF(){
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//        configuration.setHostName("127.0.0.1");
//        configuration.setPort(6379);
//        //用db1做jedis库
//        configuration.setDatabase(1);
//        return new JedisConnectionFactory(configuration);
//    }
//}
