package com.taotao.order.service.impl;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.component.JedisClient;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_ID_BEGIN}")
    private String ORDER_ID_BEGIN;
    @Value("${REDIS_ORDER_DETAIL_GEN_KEY}")
    private String REDIS_ORDER_DETAIL_GEN_KEY;
    @Value("${REDIS_ORDER_GEN_KEY}")
    private String REDIS_ORDER_GEN_KEY;

    //创建订单接口
//    一、插入订单表
//1、接收数据OrderInfo
//2、生成订单号， 使用redis的incr命令生成
//3、补全字段
//4、插入订单表
//
//    二、插入订单明细
//1、生成订单明细id，使用redis的incr命令生成。
//            2、补全字段
//3、插入数据
//
//    三、插入物流表
//1、补全字段
//2、插入数据
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        String id=jedisClient.get(REDIS_ORDER_GEN_KEY);
        if(StringUtils.isBlank(id)){
            jedisClient.set(REDIS_ORDER_GEN_KEY,ORDER_ID_BEGIN);
        }
        final long OrderId = jedisClient.incr(REDIS_ORDER_GEN_KEY);
        orderInfo.setOrderId(String.valueOf(OrderId));
      //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        Date date=new Date();
        orderInfo.setCreateTime(date);
        orderInfo.setUpdateTime(date);
//        插入订单表
        orderMapper.insert(orderInfo);

//订单明细相关
        List<TbOrderItem> orderItems=orderInfo.getOrderItems();
        for (TbOrderItem tbOrderItem:orderItems){
            long detailId = jedisClient.incr(REDIS_ORDER_DETAIL_GEN_KEY);
            tbOrderItem.setId(String.valueOf(detailId));
            tbOrderItem.setOrderId(String.valueOf(OrderId));
            orderItemMapper.insert(tbOrderItem);
        }

//        物流表相关
        TbOrderShipping orderShipping=orderInfo.getOrderShipping();
        orderShipping.setOrderId(String.valueOf(OrderId));
        Date date2=new Date();
        orderShipping.setCreated(date2);
        orderShipping.setUpdated(date2);
        orderShippingMapper.insert(orderShipping);

        return TaotaoResult.ok(OrderId);
    }
}
