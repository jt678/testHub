package com.jt.test.junitTest;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.test.TestApplication;
import com.jt.test.domain.Person;
import com.jt.test.domain.SchoolInfo;
import com.jt.test.domain.bo.HttpRequestBO;
import com.jt.test.domain.entity.Dict;
import com.jt.test.service.DictService;
import com.jt.test.utils.Enums.GenderEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

//import static org.apache.commons.codec.digest.DigestUtils.md5;

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

    private String url = "http://192.168.1.43:8301/service/schoolInfo/test";
    private String token = "Bearer cbc76d20-f8b3-4d23-9d3a-0972f763d019";

    @Autowired
    private DictService dictService;

    @Test
    public void setTestIp() throws IOException {

        System.out.println("http://" + testIp + "/#/caLogin?code=");
//        response.sendRedirect("http://"+testIp+"/#/caLogin?code=");
    }

    /**
     * 深拷贝/浅拷贝测试
     */
    @Test
    public void copy() throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        System.out.println(hostAddress);
    }

    /**
     * ip白名单
     *
     * @throws UnknownHostException
     */
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

    /**
     * string分割
     */
    @Test
    public void splitTest() {
        String i = "1.2,32";
        List<String> stringList = Arrays.asList(i.split(","));
        System.out.println(stringList);

    }

    /**
     * hashset，hashmap测试（已插入元素修改后的操作）
     */
    @Test
    public void hashTest() {
        //建立一个hashmap一个hashset
        HashSet<Person> hashSet = new HashSet<>();
        HashMap<String, Person> hashMap = new HashMap<>();
        Person kid = new Person(1l, "小明", GenderEnum.MALE.getGender(), 13);
        Person mother = new Person(2l, "小花", GenderEnum.FEMALE.getGender(), 35);
        int oldHash = mother.hashCode();
        System.out.println("oldHash:  " + oldHash);
        hashMap.put("1", kid);
        hashMap.put("2", mother);
        hashMap.put("3", mother);
        hashMap.values().stream().spliterator().forEachRemaining(System.out::println);

        hashSet.add(kid);
        hashSet.add(mother);
        //此处把mother年龄+1，set中的mother元素年龄也+1了，同时可以再次插入修改后的mother元素----因为hash值变了
        mother.setAge(mother.getAge() + 1);
        int newHash = mother.hashCode();
        System.out.println("newHash:  " + newHash);
        //此处想要remove修改后的元素是失败的，也是因为hash值改变了，hashSet的底层hashMap无法通过hash值找到对应元素
        boolean remove = hashSet.remove(mother);
        System.out.println(remove);

        hashSet.add(mother);

        Spliterator<Person> spliterator = hashSet.stream().spliterator();
        spliterator.forEachRemaining(System.out::println);

    }

    /**
     * MD5加密
     */
    @Test
    public void mdEncode() {
        String userName = "xiaoming";
        String passWord = "Bx376288";
        String realm = "TheNextService";
        String randomKey = "21232f297a57a5a743894a0e4a801fc3:e978c44f-bbf1-496e-a596-3becd1a37970";
        //密码密文生成

        String pwd = DigestUtils.md5DigestAsHex(passWord.getBytes());

        String passwordMd5 = DigestUtils.md5DigestAsHex((userName + ":TheNextService:" + pwd).getBytes());

        //签名生成
        String preSignature = DigestUtils.md5DigestAsHex((userName + ":" + realm + ":" + passwordMd5).getBytes());

        String signature = DigestUtils.md5DigestAsHex((preSignature + ":" + randomKey).getBytes());

        System.out.println("Md5密码：" + passwordMd5 + "\n生成签名：" + signature);

    }

    /**
     * hutool调用第三方接口
     */
    @Test
    public void HttpTest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 1);
        System.out.println(map);
        String body = HttpRequest.get(url)
                .header("GatewayToken", "amllYmFvOmdhdGV3YXk6MTIzNDU2")
                .header("Authorization", token)
                .form(map)
                .execute().body();
        System.out.println(body);

        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray rows = data.getJSONArray("rows");
        JSONObject jsonObject1 = rows.getJSONObject(0);
        String dutyPerson = jsonObject1.getString("dutyPerson");
        String deleted = jsonObject1.getString("deleted");
        System.out.println(dutyPerson + "\n" + deleted);

    }

    /**
     * Hutool入参使用json调用第三方接口
     * 此处用body()放json
     */
    @Test
    public void HttpTestTwo() {
        String str = "{\"pageSize\": 1, \"pageNum\": 1}";

        String body = HttpRequest.get(url)
//                .header("GatewayToken", "amllYmFvOmdhdGV3YXk6MTIzNDU2")
                .header("Authorization", token)
                .body(str)
                .execute().body();

        JSONObject jsonObject = JSONObject.parseObject(body);
        System.out.println(jsonObject);
    }

    /**
     * Hutool使用实体类转嵌套HashMap请求第三方接口（X○X），
     * 在嵌套层name直接加当前层的名称加参数传进去就行了，无须封装多层HashMap
     */
    @Test
    public void HttpTestThree() {
//        //建立第一层hashmap和所需的入参
//        //第一层入参
//        HttpRequestBO bo = new HttpRequestBO();
//        //第二层入参
//        SchoolInfo param = new SchoolInfo();
//        param.setSchoolName("湖南大学");
//        String value = JSON.toJSONString(param);
//        System.out.println(value);
//
//        //第一,二层hashmap
//        HashMap<String, Object> bomap = new HashMap<>();
        String shoolName = "湖南大学";

        String body = HttpRequest.get(url)
                .header(Header.AUTHORIZATION, token)
                .form("pageNum", 1)
                .form("pageSize", 2)
                .form("param.schoolName", shoolName)
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        System.out.println(jsonObject);
    }
}
