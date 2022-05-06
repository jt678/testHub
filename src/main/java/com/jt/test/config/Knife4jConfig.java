package com.jt.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * Knife4jConfig
 *
 * @author jt
 * @date 2022/4/12
 **/
@Configuration

public class Knife4jConfig {
    /**
     * 定义test1组的接口
     * @return
     */
    @Bean(value = "测试接口")
    public Docket testApi(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("test1")
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jt.test.controller"))
                .build();

    }

    /**
     * 定义整个项目信息
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("用于测试学习的项目")
                .description("描述")
                .version("1.0")
                .contact(new Contact("jt","baidu.com","6666@jt.com"))
                .termsOfServiceUrl("baidu.com")
                .build();
    }
}
