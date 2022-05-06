package com.jt.test.convert;

import com.jt.test.domain.Order;
import com.jt.test.domain.bo.OrderBO;
import com.jt.test.domain.dto.OrderDTO;
import com.jt.test.domain.vo.OrderVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
;import java.util.List;

/**
 * OrderConvert
 *
 * @author jt
 * @date 2022/3/10
 **/

/**
 * componentModel有以下几种取值，一般用的比较多的是spring
 * default: 默认取值，mapstruct 不使用任何组件类型, 我们在获取生成实例的时候，可以通过Mappers.getMapper(Class)方式获取
 * spring: 生成的实现类上面会自动添加一个@Component注解，可以通过Spring的 @Autowired方式进行注入
 * cdi: 生成的映射器是一个应用程序范围的 CDI bean，可以通过 @Inject 检索
 * jsr330: 生成的实现类上会添加@javax.inject.Named 和@Singleton注解，可以通过 @Inject注解获取
 */

@Mapper(componentModel = "spring")
public interface OrderConvert {
    /**
     * entityList转voList
     */
    OrderVO entityToVO (Order order);

    /**
     * 查询条件处理
     */
    @Mapping(target = "conditions",
            expression = "java(cn.hutool.core.util.StrUtil.isNotEmpty(bo.getConditions())? java.util.Arrays.asList(bo.getConditions().toUpperCase().split(\",\")):null)")

    OrderDTO boToDto (OrderBO bo);

    List<OrderVO> entityListToVoList(List<Order> orderList);
}