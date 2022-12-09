package com.jt.test;

import com.jt.test.demo1.MyApplicationContextInitializer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author j
 * @date 2022/3/8
 */
//使用这个只能使用swagger，不能解决knife4j的问题
//@EnableWebMvc
@EnableScheduling
@SpringBootApplication
@MapperScan("com.jt.test.demo1.mapper")
public class TestApplication {
    //这些args是启动参数，比如java -jar --spring.profiles.active=prod后面的--spring.profiles.active=prod---(存疑)
    public static void main(String[] args) {
//        args = new String[]{"--spring.profiles.active=prod"};
        SpringApplication springApplication = new SpringApplication(TestApplication.class);
        springApplication.addInitializers(new MyApplicationContextInitializer());

        springApplication.run(args);
//        System.out.println("===========================================启动成功===========================================");
    }


}
