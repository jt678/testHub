package com.jt.test.demo1.factory;

import com.jt.test.demo1.service.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * LoginStrategyFactory
 * 登录策略工厂，在这里根据type得到对应的实例，调用不通的处理逻辑
 *
 * 其实就是个启动类，继承了CommandLineRuner，类似ApplicationRunner，进启动类的run方法一看就明了了
 * @Author: jt
 * @Date: 2022/12/15 16:36
 */
@Component
public class LoginStrategyFactory implements CommandLineRunner {

    /**
     * 用来存放不同策略的数组
     */
    private final List<LoginStrategy> strategies = new ArrayList<>();

    @Autowired
    private ApplicationContext applicationContext;

    public String login(String type, String username, String password) {
        for (LoginStrategy strategy : strategies) {
            if (type.equals(strategy.getType())) {
                return strategy.login(username, password);
            }
        }
        return "错误的登录类型,登录失败";
    }

    public List<String> getTypes(){
        List<String> list = new ArrayList<>();
        for (LoginStrategy strategy : strategies) {
            list.add(strategy.getType());
        }
        return list;
    }

    @Override
    public void run(String... args) throws Exception {
        //ComponentScan注解扫描到的bean，class会立刻被解析成BeanDefinition，添加进BeanDefinitionNames属性中
        String[] beans = applicationContext.getBeanDefinitionNames();
        // 将所有登录策略组装进策略列表中
        for (String bean : beans) {
            Object beanObj = applicationContext.getBean(bean);
            //instanceof关键字用来判断beanObj对象是否是LoginStrategy类的实例，这个关键字别忘记了

            if (beanObj instanceof LoginStrategy){
                strategies.add((LoginStrategy)beanObj);
            }
        }
    }
}
