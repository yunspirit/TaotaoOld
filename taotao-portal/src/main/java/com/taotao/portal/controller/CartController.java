package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.impl.CartRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
//    @Autowired
//    private CartRedisService cartRedisService;
    //购物车中添加商品
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable long itemId, Integer num,
                          HttpServletResponse response,HttpServletRequest request){
            TaotaoResult result=cartService.addCart(itemId,num,request,response);
            return "cartSuccess";
    }

//    从cookie中取得商品列表并返回  展示购物车列表
    @RequestMapping("/cart/cart")
    public  String showCartList(HttpServletRequest request,Model model){
        List<CartItem> list=cartService.getCartItems(request);
        model.addAttribute("cartList",list);
        return "cart";
    }

    //    更新购物车
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateCartItem(@PathVariable  long itemId, @PathVariable int num, HttpServletRequest request, HttpServletResponse response){
        try{
                return cartService.addCart(itemId,num,request,response);
        }catch (Exception e){
            return  TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

    //    删除购物车
    @RequestMapping("/cart/delete/{itemId}")
    private String deleteCartItem(@PathVariable long itemId,HttpServletRequest request,HttpServletResponse response){
        try{
            TaotaoResult result= cartService.deleteCartItem(itemId,request,response);
//            直接返回cart 还需要填写model的属性 麻烦 不如直接调用之前的逻辑请求
            return "redirect:/cart/cart.html";
        }catch (Exception e){
            return "redirect:/cart/cart.html";
        }
    }
}
