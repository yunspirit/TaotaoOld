package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {
    TaotaoResult addCart(long itemId, int num, HttpServletRequest request, HttpServletResponse response);
    List<CartItem> getCartItems(HttpServletRequest request);
    TaotaoResult updateCartItem(long itemId,int num,HttpServletRequest request, HttpServletResponse response);
    TaotaoResult deleteCartItem(long itemId,HttpServletRequest request,HttpServletResponse response);
}
