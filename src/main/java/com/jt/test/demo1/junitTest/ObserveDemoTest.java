package com.jt.test.demo1.junitTest;

import com.jt.test.TestApplication;
import com.jt.test.demo1.service.observe.EmailObserver;
import com.jt.test.demo1.service.observe.impl.EmailSystem;
import com.jt.test.demo1.service.observe.impl.EmailUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * ObserveDemoTest
 *
 * @Author: jt
 * @Date: 2023/9/5 14:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@Slf4j
public class ObserveDemoTest {
    @Test
    public void EmailDemo() {
        EmailSystem emailSystem = new EmailSystem();
        //假如这是从redis或者mysql中拿到的数据
        List<EmailObserver> userList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("user");
        for (int i = 0; i < 3; i++) {
            EmailObserver user = new EmailUser((long) i, sb.append(i).toString());
            userList.add(user);
            sb.setLength(0); // 清空StringBuilder的值
            sb.append("user");
        }
        emailSystem.registerObservers(userList);
        emailSystem.removeObserver(userList.get(1));
        emailSystem.notifyObservers("Hello World!");
    }
}
