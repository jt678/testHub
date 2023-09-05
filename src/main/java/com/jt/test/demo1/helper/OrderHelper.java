package com.jt.test.demo1.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.test.demo1.common.HttpResult;
import com.jt.test.demo1.convert.OrderConvert;
import com.jt.test.demo1.domain.Coffee;
import com.jt.test.demo1.domain.entity.MemberPrice;
import com.jt.test.demo1.domain.entity.Order;
import com.jt.test.demo1.domain.bo.OrderBO;
import com.jt.test.demo1.domain.dto.OrderDTO;
import com.jt.test.demo1.domain.vo.OrderVO;

import com.jt.test.demo1.service.factory.CoffeAbstractFactory;
import com.jt.test.demo1.service.MemberPriceService;
import com.jt.test.demo1.service.OrderService;
import com.jt.test.demo1.service.RedisService;
import com.jt.test.demo1.service.factory.impl.AmericanoFactroy;
import com.jt.test.demo1.service.factory.impl.CoffeeStore;
import com.jt.test.demo1.service.factory.impl.LatteFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * OrderHelper
 *
 * @author jt
 * @date 2022/3/8
 **/
@Service
@Slf4j
public class OrderHelper {

    @Autowired
    private OrderService service;
    @Autowired
    private OrderConvert convert;
    @Autowired
    private  MemberPriceHelper memberPriceHelper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MemberPriceService memberPriceService;


    public HttpResult<OrderVO> test(Long id) {

        Order order = service.getById(id);
        OrderVO orderVO = convert.entityToVO(order);
        return HttpResult.success(orderVO);
    }

    /**
     * 条件查询
     *
     * @return
     */
    public HttpResult<List<OrderVO>> list(OrderBO bo) {
        List<OrderVO> voList = new ArrayList<>();
        OrderDTO orderDTO = convert.boToDto(bo);

        //查entity数据
        IPage<Order> page = new Page<>(bo.getPageNum(), bo.getPageSize());
        IPage<Order> result = service.listBySearch(page, orderDTO);

        List<Order> orderList = result.getRecords();
        voList = convert.entityListToVoList(orderList);
        return HttpResult.success(result.getTotal(), voList);
    }

//    /**
//     * redis存储订单热点字段(redisTemplate)
//     */
//    @Transactional(rollbackFor = Exception.class)
//    public HttpResult<List<OrderVO>> listHotPot() {
//
//
//
//        redisTemplate.opsForValue().set("测试","2022/12/6");
//
//
//
//        String test = redisTemplate.opsForValue().get("测试");
//
//        System.out.println(test);
//        return HttpResult.success();
//    }

    /**
     * redis存储订单热点字段（封装后的redisTemplate）
     */
    @Transactional(rollbackFor = Exception.class)
    public HttpResult<List<OrderVO>> listHotPot() {
        //现在redis里面查，查不到之后查数据库
        String hotPot = String.valueOf(redisService.get("hotSpot"));
        if (!"null".equals(hotPot)) {
            //转回对象
            List<OrderVO> orderVOList = JSON.parseArray(hotPot, OrderVO.class);
            return HttpResult.success((long) orderVOList.size(), orderVOList);
        }

        //第一次查，把查到的数据放到redis，设置一小时过期
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.select("id", "member_id", "coupon_id", "order_sn", "create_time", "modify_time");
        List<Order> list = service.list(wrapper);
        List<OrderVO> orderVOList = convert.entityListToVoList(list);
        String voString = JSONObject.toJSON(orderVOList).toString();
        redisService.set("hotSpot", voString, 3600);
        return HttpResult.success((long) orderVOList.size(), orderVOList);

    }

    /**
     * 更新或新增订单---用来测redis和mysql同步的问题
     */
    public HttpResult saveOrUpdate(Order order) {
        Date now = new Date();
        Long id = order.getId();
        if (id == null) {
            order.setCreateTime(now);
        } else {
            order.setModifyTime(now);
        }
        //先更新db后删除缓存
        boolean bool = service.saveOrUpdate(order);
        redisService.del("hotSpot");

        return  bool? HttpResult.success() : HttpResult.failed();
    }

    @Transactional(rollbackFor = Exception.class)
    public void rollBackTest(Order order) throws ExecutionException, InterruptedException {
        //测试Note为null出现NPE问题
        service.save(order);
        if (order.getNote().equals("hh")){
            order.setNote("changed");
            service.updateById(order);
        }
        //异步新增另外一条数据
         this.dealMemberPrice1(order);
         memberPriceHelper.dealMemberPrice2(order);

    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    public Future<String> dealMemberPrice1(Order order) {
        if (order.getOrderSn().equals("c1")){
            MemberPrice memberPrice = new MemberPrice();
            memberPrice.setMemberLevelName("c1等级");
            memberPriceService.save(memberPrice);
        }
        return new AsyncResult<String>("异步任务1完成");
    }

    /**
     * 点单A，专门消费美式类订单方法
     */
    @Test
    public void consumerA(){
        CoffeAbstractFactory factroy = new AmericanoFactroy();
        CoffeeStore coffeeStore = new CoffeeStore(factroy);
        Coffee coffee = coffeeStore.orderCoffee();
        System.out.println("消费者消费了"+coffee.getName());
    }

    /**
     * 点单B，专门消费拿铁类订单方法
     * 此种工厂模式相比简单工厂在选择具体产品时候并不需要在抽象工厂中做判断，
     * 而是由消费方通过初始化想要的产品对应的工厂来生产，如果后续有新产品C，调用方只需要
     * 新增C具体工厂和C具体产品类，无需修改抽象工厂代码，符合开闭原则
     *
     */
    @Test
    public void consumerB(){
        LatteFactory factory = new LatteFactory();
        CoffeeStore coffeeStore = new CoffeeStore(factory);
        Coffee coffee = coffeeStore.orderCoffee();
        System.out.println("消费者消费了"+coffee.getName());
    }
}
