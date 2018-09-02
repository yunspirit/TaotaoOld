package com.taotao.portal.service;


import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.OrderInfo;

public interface OrderService {
   String createOrder(OrderInfo orderInfo);
}
