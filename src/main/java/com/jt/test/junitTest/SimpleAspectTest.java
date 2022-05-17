package com.jt.test.junitTest;

import com.jt.test.TestApplicationMapTest;
import com.jt.test.aspect.SimpleAspect;
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
// TODO: 2022/5/17 测试一直爆红，显示无法找到simpleAspectController 的bean
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationMapTest.class)
public class SimpleAspectTest {
    @Test
    public void testSimpleAspect(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AopConfig.class);
        SimpleAspectController simpleAspectController = (SimpleAspectController) annotationConfigApplicationContext.getBean("simpleAspectController");
        simpleAspectController.aspectTest();
    }
}
