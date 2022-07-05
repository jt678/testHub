package com.jt.test.junitTest;

import com.jt.test.TestApplication;
import com.jt.test.config.AopConfig;
import com.jt.test.controller.SimpleAspectController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SimpleAspectTest
 *
 * @author jt
 * @date 2022/5/17
 **/
// TODO: 2022/5/17 测试一直爆红，显示无法找到simpleAspectController 的bean 是因为springboot过高和knife4j版本pathmatch设置不一样，在yml里面改一下就可以
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class SimpleAspectTest {
    @Test
    public void testSimpleAspect(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AopConfig.class);
        SimpleAspectController simpleAspectController = (SimpleAspectController) annotationConfigApplicationContext.getBean("simpleAspectController");
        simpleAspectController.aspectTest();
    }
}
