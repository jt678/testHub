package com.jt.test.junitTest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.test.TestApplication;
import com.jt.test.domain.entity.Company;
import com.jt.test.domain.entity.Dict;
import com.jt.test.service.DictService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试草稿
 *
 * @Author: jt
 * @Date: 2022/10/25 15:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class DraftTest {
    @Value("${jt.mqtt.test_ip}")
    private String testIp;

    @Autowired
    private DictService dictService;

    @Test
    public void setTestIp(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://"+testIp+"/#/caLogin?code=");
    }

    /**
     * 深拷贝/浅拷贝测试
     */
    @Test
    public void copy() throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        System.out.println(hostAddress);
    }

    @Test
    public void preDocumentDetail() throws UnknownHostException {
        //查ip白名单字典
        List<String> ipWhiteList = dictService.list(new LambdaQueryWrapper<Dict>().eq(Dict::getNumbering, "ip_white_list")).stream().map(Dict::getDictValue).collect(Collectors.toList());

        //截取Ipv4前三段
        String ipv4 = InetAddress.getLocalHost().getHostAddress();
        List<String> ipArrayList = Arrays.asList(ipv4.split("\\."));
        String ipv3 = ipArrayList.get(0) + "." + ipArrayList.get(1) + "." + ipArrayList.get(2);
        System.out.println((ipWhiteList.contains(ipv4) || ipWhiteList.contains(ipv3)));
    }
}
