package com.jt.test.demo1.junitTest;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.jt.test.TestApplication;
import com.jt.test.demo1.domain.entity.Brand;
import com.jt.test.demo1.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;

/**
 * HutoolUtilsTest
 *
 * hutool工具包
 * @Author: jt
 * @Date: 2022/7/14 15:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@Slf4j
public class HutoolUtilsTest {
    @Autowired
    private BrandService brandService;
    /**
     * HTTP UTIL
     *
     */
    @Test
    public void httpUtilTest() throws IOException {
//        String s1 = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/user/get?access_token=58_WBBHugdYRXPUxALWaPctPuF7JFvzqXkKd-BiA2MFgxSkkRr_Rk1cJM0Wn46c5ZDrEUAvga336FB8QisPyH5hBMkVfjfsEKT0bxuo8c0n8LNCh50MzbubfFjTSPD9-7LxuYXAjljM7NYm8XuOKDMdAFATMV");
//        System.out.println(s1);
//        String a = "1 2 3 4 5 a s v  d----1";
//        List<String> stringList = StringUtils.str2List(a, " ", true, true);
//        System.out.println(stringList);
        //GET请求测试
        System.out.println(HttpUtil.get("baidu.com"));

        System.out.println(HttpUtil.get("baidu.com", CharsetUtil.CHARSET_ISO_8859_1));

        HashMap<String, Object> addressMap = new HashMap<>(); //为什么这个地方map的Value要用Object
        addressMap.put("city","上海");
        System.out.println(HttpUtil.get("baidu.com", addressMap));

        //POST请求测试
        System.out.println(HttpUtil.post("baidu.com", addressMap));
        //文件上传
        HashMap<String, Object> fileMap = new HashMap<>();
        //文件上传需将参数的键名为file（默认为file），值设置为文件对象
        fileMap.put("file", FileUtil.file("D:\\Users\\Desktop\\头像.jpg"));
        System.out.println(HttpUtil.post("baidu.com", fileMap));

        //文件下载到指定目录
        String fileUrl = "https://repo1.maven.org/maven2/me/chanjar/weixin-java-common/1.3.3/weixin-java-common-1.3.3.jar";
        HttpUtil.downloadFile(fileUrl,FileUtil.file("E:\\"));


        HttpResponse res = HttpRequest.get("baidu.com").execute();
        log.info(String.valueOf(res.getStatus()));
    }

        @Test
        public void TT(){
            Brand brand = new Brand();
            Brand byId = brandService.getById(1l);
            brand.setId(1l);
            brand.setName("tttttt");
            brand.setFirstLetter("zzzzzz");
            brandService.saveOrUpdate(brand);

        }

}
