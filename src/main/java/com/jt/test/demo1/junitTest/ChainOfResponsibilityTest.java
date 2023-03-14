package com.jt.test.demo1.junitTest;

import com.jt.test.TestApplication;
import com.jt.test.demo1.domain.ConcreteHandler1;
import com.jt.test.demo1.domain.ConcreteHandler2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ChainOfResponsibilityTest
 *  责任链模式实现
 * @Author: jt
 * @Date: 2023/2/8 16:43
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class ChainOfResponsibilityTest {

    @Test
    public  void ChainOfResponsibility(){
        ConcreteHandler1 handler1 = new ConcreteHandler1();
        ConcreteHandler2 handler2 = new ConcreteHandler2();
        handler1.setNext(handler2);
        handler1.handleRequest("2");
    }
}
