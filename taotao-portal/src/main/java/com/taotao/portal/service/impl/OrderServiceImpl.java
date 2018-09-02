package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_BASE_URL}")
    private  String ORDER_BASE_URL;
    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;

    //接收生成订单请求 调用order服务 生成订单到数据库
    @RequestMapping("")
    public String createOrder(OrderInfo orderInfo){
        String json=JsonUtils.objectToJson(orderInfo);
        String jsonResult=HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL,json);
        TaotaoResult taotaoResult=TaotaoResult.format(jsonResult);
        String orderId=taotaoResult.getData().toString();
        return orderId;
    }
}
