package com.taotao.portal.controller;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    //订单确认  从购物车中取得商品并显示
    @RequestMapping("/order-cart")
    public String showOrderCart(Model model, HttpServletRequest request){
        List<CartItem> cartItemList=cartService.getCartItems(request);
        model.addAttribute("cartList",cartItemList);
        return "order-cart";
    }

//    生成订单成功
    @RequestMapping(value = "/create",method =RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo,Model model,HttpServletRequest request) {
        TbUser user=(TbUser) request.getAttribute("user");
//        补全OrderInfo的属性
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        String orderId=orderService.createOrder(orderInfo);
//        传递订单号信息
        model.addAttribute("orderid",orderId);
        model.addAttribute("payment",orderInfo.getPayment());
        DateTime dateTime=new DateTime();
        DateTime dateTime1=dateTime.plusDays(3);
        model.addAttribute("date",dateTime1.toString("yyyy-MM-dd"));
//        返回逻辑视图
        return "success";
    }


}
