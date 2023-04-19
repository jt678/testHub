package com.jt.test.demo1.junitTest;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.test.TestApplication;
import com.jt.test.demo1.convert.RoleConvert;
import com.jt.test.demo1.domain.Dog;
import com.jt.test.demo1.domain.Person;
import com.jt.test.demo1.domain.entity.Dict;
import com.jt.test.demo1.domain.entity.Order;
import com.jt.test.demo1.domain.entity.Role;
import com.jt.test.demo1.service.*;
import com.jt.test.demo1.utils.Enums.GenderEnum;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//import static org.apache.commons.codec.digest.DigestUtils.md5;

/**
 * 测试草稿
 *
 * @Author: jt
 * @Date: 2022/10/25 15:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class DraftTest {
    @Value("${jt.mqtt.test_ip}")
    private String testIp;

    private String url = "http://192.168.1.43:8301/service/schoolInfo/test";
    private String token = "bearer 29903f04-1023-4406-95e7-87a9e2837ab9";

    @Autowired
    private DictService dictService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleConvert roleConvert;

    //如果Autowired和map使用的话呢，它将它bean的名称作为key,所有的bean作为value.
    @Autowired
    private Map<String, LoginStrategy> animalBeanMap = new HashMap<>();

    //当Autowired注解在Collection 上时，会将所申明类的所有实现类都放在那个指定的Collection里.
    @Autowired
    private List<LoginStrategy> animalList = new ArrayList<>();
    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderService orderService;

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
        //forEachRemaining输出集合中剩下的元素
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
    public void httpTest() {
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
    public void httpTestTwo() {
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
    public void httpTestThree() {
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

    /**
     * 实体类List的stream流筛选字段操作
     * 业务描述：从第三方接口获得的数据对比当前表，更新当前数据库内容（删除多余的，更新更新内容,新增新增内容）
     */
    @Test
    public void streamFilter() {
        Date now = new Date();
        //表里全量的数据
        List<Role> list = roleService.list();
        List<String> nameList = list.stream().map(Role::getName).collect(Collectors.toList());
        Map<String, Long> nameIdMap = list.stream().collect(Collectors.toMap(Role::getName, Role::getId, (e1, e2) -> e1));
        Map<String, Integer> nameSortMap = list.stream().collect(Collectors.toMap(Role::getName, Role::getSort, (e1, e2) -> e1));

        //造测试数据
        List<Role> testDataList = new ArrayList<>();
////        Role adminT = new Role(null, "超级管理员t", "测试数据1", 0, null, 1, 1);
////        Role orderT = new Role(null, "订单管理员t", "测试数据2", 0, null, 1, 2);
////
////        Role shop = new Role(null, "商品管理员", "只能查看及操作商品", 0, null, 1, 0);
////        Role order = new Role(null, "订单管理员", "只能查看及操作订单", 0, null, 0, 0);
////        Role admin = new Role(null, "超级管理员", "拥有所有查看和操作功能", 0, null, 1, 0);
//        testDataList.add(shop);
//        testDataList.add(order);
//        testDataList.add(admin);
//
//        testDataList.add(adminT);
//        testDataList.add(orderT);

        //筛选数据库里name与测试数据中name相同的数据
        List<Role> sameRoleList = new ArrayList<>();
        for (Role role : testDataList) {
            String name = role.getName();
            if (nameList.contains(name)) {
                role.setId(nameIdMap.get(name));
                //如果sort(某一`字段)有变化，说明更新过数据，就放入新的时间
                if (!role.getSort().equals(nameSortMap.get(name))) {
                    role.setCreateTime(now);
                }

                sameRoleList.add(role);
            }
        }

//        List<Role> sameRoleList = testDataList.stream().filter(item -> nameList.contains(item.getName())).collect(Collectors.toList());
        //更新数据
        roleService.updateBatchById(sameRoleList);
        System.out.println(sameRoleList);

        //新增数据
        List<Role> newRoleList = roleConvert.test2New(testDataList);

        newRoleList.removeAll(sameRoleList);

        if (!newRoleList.isEmpty()) {

            //插入自动生成的字段
            for (Role role : newRoleList) {
                role.setCreateTime(now);
            }
            roleService.saveBatch(newRoleList);
        }
        //旧数据删除
        //得到新增的和删除的
//        list.removeAll(sameRoleList);
        List<String> sameNameList = sameRoleList.stream().map(Role::getName).collect(Collectors.toList());
        List<String> newNameList = newRoleList.stream().map(Role::getName).collect(Collectors.toList());

        List<Role> addDelCollect = list.stream().filter(item -> !sameNameList.contains(item.getName())).collect(Collectors.toList());
        //得到删除的
//        list.removeAll(newRoleList);
        List<Role> delList = addDelCollect.stream().filter(item -> !newNameList.contains(item.getName())).collect(Collectors.toList());
        if (!delList.isEmpty()) {
            List<Long> delIdList = delList.stream().map(Role::getId).collect(Collectors.toList());
            roleService.removeByIds(delIdList);
        }

    }

    /**
     * 流获取max，min，sum，avg
     */
    @Test
    public void isBlankTest() {
        Double max = null;
        Double min = null;
        Double sum = null;
        Double avg = null;

        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("1.23"));
        dogs.add(new Dog("2.23"));
        dogs.add(new Dog("3.23"));
        dogs.add(new Dog("5.23"));

        List<String> list = dogs.stream().map(Dog::getName).collect(Collectors.toList());
        List<Double> dogAgeList = list.stream().mapToDouble(Double::parseDouble).boxed().collect(Collectors.toList());

        Optional<Double> optionalMax = dogAgeList.stream().reduce(Double::max);
        Optional<Double> optionalMin = dogAgeList.stream().reduce(Double::min);
        Optional<Double> optionalSum = dogAgeList.stream().reduce(Double::sum);
        if (optionalSum.isPresent()) {
            sum = optionalSum.get();
        }

        if (sum != null && sum.isInfinite()) {
            BigDecimal decimalSum = BigDecimal.valueOf(sum);
            BigDecimal decimalSize = BigDecimal.valueOf(dogAgeList.size());
            DecimalFormat df = new DecimalFormat("#.00");

            avg = Double.valueOf(df.format(decimalSum.divide(decimalSize)));
        }
        if (optionalMax.isPresent()) {
            max = optionalMax.get();
        }

        if (optionalMin.isPresent()) {
            min = optionalMin.get();
        }

//        这种也可以用，但是实体类对应的要是Integer,Long,Double,这里测试没创建新实体类的，但是在开发中使用的是此方法
//        DoubleSummaryStatistics collect = dogAgeList.stream().collect(Collectors.summarizingDouble(Dog::getName));
//        double average = collect.getAverage();
        System.out.println("max:" + max);
        System.out.println("min:" + min);
        System.out.println("avg:" + avg);
    }

    /**
     * 在上个草稿上发现的bug,Math.max() min()
     * 如果没有参数的话Double会返回-infinity和infinity
     * Int会返回-2147483648和2147483647============》int的取值范围-2147483648——2147483647
     */
    @Test
    public void mathTest() {

        List<Role> roleList = roleService.list();
        List<Role> nullRoleList = roleList.stream().filter(item -> item.getSort() == 21312321).collect(Collectors.toList());
        IntSummaryStatistics collect = nullRoleList.stream().collect(Collectors.summarizingInt(Role::getSort));
        System.out.println("MAX:" + collect.getMax());
        System.out.println("MIN:" + collect.getMin());
        System.out.println("AVG:" + collect.getAverage());
        System.out.println("SUM:" + collect.getSum());
        System.out.println("COUNT:" + collect.getCount());

    }

    /**
     * 访问45服务器上接口
     */
    @Test
    public void remoteApi() {
        String url = "http://192.168.1.45:8301/service/external/assessment/assessmentList";
        HashMap<String, Object> param = new HashMap<>();
        //能直接放参数，原分页参数是在自己定义的queryRequest下的，我们直接用k-v形式传不用管数据结构也能完成
        param.put("pageNum", 1);
        param.put("pageSize", 10);
        param.put("idCode", "JCY-430900-00114");
        String result = HttpRequest.post(url).form(param).execute().body();
        String jsonString = JSON.toJSONString(result);
        System.out.println(jsonString);

    }

    /**
     * hashMap测试
     */
    @Test
    public void HashMapTest() {
        Map<String, Integer> testMap = new HashMap<>();

        //构造map
        testMap.put("assz2", 1132132145);
        testMap.put("bxz12", 2321355);
        testMap.put("xzaaw2c", 3321567);
        testMap.put("ddsaz23", 432131);
        testMap.put("sadz21", 213213);
        testMap.put("sadaz2231", 32144519);
        testMap.put("zxpi12", 30009321);
        testMap.put("samkoo12", 2131245);

        //String的hashCode检测，发现string重写了hashcode是根据字符数组的内容来确定hashcode，内容一样hashcode也一样，但是用==比较内存地址会不同，返回false
        String i = "h312213312aaaa";
        int i1 = i.hashCode();

        String j = "h312213312aaaa";
        int i2 = j.hashCode();

//        三种视图
        Set<Map.Entry<String, Integer>> entrySet = testMap.entrySet();
        Set<String> keySet = testMap.keySet();
        Collection<Integer> values = testMap.values();

//        迭代器
        Iterator<Map.Entry<String, Integer>> entryIterator = entrySet.iterator();
        Iterator<String> keyIterator = keySet.iterator();
        Iterator<Integer> valuesIterator = values.iterator();

        while (entryIterator.hasNext()) {
            Map.Entry<String, Integer> mapEntry = entryIterator.next();
            System.out.println("K: " + mapEntry.getKey() + " " + "V:" + mapEntry.getValue());
        }
        testMap.clear();

    }

    /**
     * Autowired配合list和map测试
     */
    @Test
    public void AutowiredTest() {
        LoginStrategy qqStrategyImpl = animalBeanMap.get("qqStrategyImpl");
        LoginStrategy wechatStrategyImpl = animalBeanMap.get("wechatStrategyImpl");
        String qqLogin = qqStrategyImpl.login("test", "test");
        String wechatLogin = wechatStrategyImpl.login("test", "test");
        System.out.println(qqLogin + "\n" + wechatLogin);
        animalList.forEach(System.out::println);
    }

    /**
     * redis哨兵集群连接测试
     */
    @Test
    public void RedisSentinelTest() {
        redisService.set("message", "update from springboot when 82 sentinel is down");
        redisService.set("message2", "update from springboot when 82 redis is down!!");

        System.out.println("set成功");
    }

    /**
     * 比较集合中isEmpty和size==0的区别
     */
    @Test
    public void EmptySizeDiffTest() {
        List<String> list = new ArrayList<>();
        list.add("");
        //通过源码可知，时间复杂度为O(1)
        boolean empty = list.isEmpty();
        //不确定，时间复杂度最坏为O(n)
        boolean ifEmpty = list.size() == 0;
        System.out.println(empty);
        System.out.println(ifEmpty);

    }

    @Test
    public void DateTest() throws ParseException {
//        WeekFields weekFields= WeekFields.ISO;
//        //获取到时间
//        LocalDate now = LocalDate.now();
//        //周一记作第一天
//        LocalDate mondayDate = now.with(weekFields.dayOfWeek(), 1L);
//        LocalTime time = LocalTime.of(0, 0, 0);
//        //根据时间算出那一周的第一天
//        LocalDateTime monday = LocalDateTime.of(mondayDate, time);
//        System.out.println(monday);

//        String year = "2023";
//        String startDateStr = year + "-01-01";
//        String endDateStr = year + "-12-31";
//        int weekOfYear = 53;
//        for (int i = 0; i < weekOfYear; i++) {
//            DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate startLocalDate = LocalDate.parse(startDateStr,formatter);
//            if (i == 0){
//                i+=1;
//            }
//            LocalDate endLocalDate = startLocalDate.plusWeeks(1).minusDays(1);
//            startDateStr = endLocalDate.plusDays(1).toString();
//            System.out.println(startLocalDate);
//            System.out.println(endLocalDate);
//        }

//        LocalDateTime firstDayOfMonth = LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
//        LocalDateTime lastDayOfMonth = LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
//        LambdaQueryWrapper<Order> query = new LambdaQueryWrapper<>();
//        query.ge(Order::getCreateTime,firstDayOfMonth);
//        query.le(Order::getCreateTime,lastDayOfMonth);
//        List<Order> list = orderService.list(query);
//        System.out.println(list);
//        System.out.println(firstDayOfMonth);
//        System.out.println(lastDayOfMonth);
        String date = "2023-01-07 13:20:00";
        String[] dateArray = date.split(" ");
        String time = Arrays.asList(dateArray).get(1);
        System.out.println(time);
    }

    /**
     * 获取jar包所在路径
     */
    @Test
    public void getJarPath() {
        String path = System.getProperty("user.dir");
        System.out.println(path);
    }


    /**
     * 幻读问题
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void readTest() {
        Order before = orderService.list().get(0);
        System.out.println("旧note" + before.getNote());
        before.setNote("哈哈哈，脏数据2");
        //1.更新之后去查
        Long id = before.getId();
        orderService.updateById(before);
        Order after = orderService.getById(id);
        System.out.println("新note" + after.getNote());

        //2.new
        Order order1 = new Order();
        order1.setNote("哈哈哈2");
        order1.setMemberId(1l);
        orderService.save(order1);

        System.out.println(order1.getId());
    }

    /**
     * List和Array
     */
    @Test
    public void StringTest() {
        String[] strings1 = {"1", "2", "3"};

        List<String> stringList = new ArrayList<>(Arrays.asList(strings1));
        stringList.add("4");
        String[] strings2 = stringList.toArray(strings1);
        System.out.println(strings2);
    }

    /**
     * 遇到list ADD null的问题
     */
    @Test
    public void listAdd() {
        ArrayList list = new ArrayList();

        list.add(roleService.getById(112));
        list.add(roleService.getById(113));
        list.removeIf(Objects::isNull);
        System.out.println(list);
        List<String> strList = new ArrayList<>();
        if (!list.isEmpty()) {

            String listStr = JSON.toJSONString(list);
            System.out.println(listStr);
            strList.add(listStr);
            System.out.println(strList);
        }


//        if (!listStr.contains("[]")) {
//            strList.add(listStr);
//        }
//        System.out.println(strList);
    }


    /**
     * File方法
     */
    @Test
    public void dateTest() {
        File file = new File("D:\\Users\\Desktop\\test\\test.txt");
        if (file.exists()){
            System.out.println(file.getName());
        }else {
            File directory = new File(file.getParent());
            directory.mkdirs();
            System.out.println("创建文件夹" + directory.getName());
            //创建文件
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("创建文件"+file.getName());
        }
    }

    /**
     * writer写入文件
     */
    @Test
    public void write(){
        File file = new File("D:\\Users\\Desktop\\test", "write.txt");
        if (file.exists()){
            file.delete();
        }
        try {
            //创建此文件
            file.createNewFile();
            //把file文件转成file输出流放入输出writer里，再放入输出writer里,这个地方设置勒文件流字符集为16进制，但是形成txt打开还是utf-8的
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-16")));
//            printWriter.print();
            printWriter.println("使用printWriter");
            char[] Array = {'使','用','a','b','c'};
            printWriter.print(Array);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
