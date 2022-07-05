package com.jt.test.junitTest;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.jt.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * SendEmailController
 *
 * @author jt
 * @date 2022/5/7
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class SendEmailTest {
    @Test
    public void SendEmail(){
        MailAccount account = new MailAccount();
        account.setHost("smtp.qq.com");
        account.setPort(996);
        account.setAuth(true);
        account.setFrom("510791331@qq.com");
        account.setUser("510791331@qq.com");
        account.setPass("jt19980608..");
        MailUtil.send(account,"510791331@qq.com","测试","来自jt的测试",false);
    }
}
