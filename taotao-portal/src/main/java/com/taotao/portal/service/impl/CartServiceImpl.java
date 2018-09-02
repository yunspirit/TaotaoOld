package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ItemService itemService;

    @Value("${COOKIE_EXPIRE}")
    private String COOKIE_EXPIRE;
//1、接收商品id
//2、从cookie中购物车商品列表
//3、从商品列表中查询列表是否存在此商品
//4、如果存在商品的数量加上参数中的商品数量
//
//5、如果不存在，调用rest服务，根据商品id获得商品数据。
// 6、把商品数据添加到列表中
//7、把购物车商品列表写入cookie
//8、返回TaotaoResult
    public TaotaoResult addCart(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> itemList=getCartItemList(request);
        boolean haveFlag=false;
        for(CartItem item:itemList) {
//            cartItem.getId()为Long 包装数据类型  如果itemId也为Long  则2个比较会false
            if(item.getId().longValue()==itemId){
//                exist  商品存在
                 item.setNum(item.getNum()+num);
                 haveFlag=true;
                 break;
            }
        }
//        如果不存在商品 调用rest服务
        if (!haveFlag){
            TbItem tbItem= itemService.getItemById(itemId);
//              转换成购物车item
            CartItem cartItem=new CartItem();
            cartItem.setId(tbItem.getId());
            cartItem.setNum(num);
            cartItem.setPrice(tbItem.getPrice());
            cartItem.setTitle(tbItem.getTitle());
//            取一张图片 没有就不设置完毕
            if(StringUtils.isNotBlank(tbItem.getImage())){
                String images=tbItem.getImage();
                String []imgTmp=images.split(",");
                cartItem.setImage(imgTmp[0]);
            }
            itemList.add(cartItem);
        }

//        把购物车商品列表写入cookie  时间为2天  如果之前有数据 会被覆盖
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(itemList),Integer.parseInt(COOKIE_EXPIRE),true);
        return TaotaoResult.ok();

    }

    //展示购物车列表
    @Override
    public List<CartItem> getCartItems(HttpServletRequest request) {
        return getCartItemList(request);
    }

//    更新购物车
    public TaotaoResult updateCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {

        List<CartItem>  cartItemList=getCartItemList(request);
        for(CartItem item:cartItemList){
            if(item.getId()==itemId){
                item.setNum(num);
                break;
            }
        }
        //   写入cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(cartItemList),Integer.parseInt(COOKIE_EXPIRE),true);
        return TaotaoResult.ok();
    }

    //    取得购物车商品列表
    private List<CartItem> getCartItemList(HttpServletRequest request){
        try {
            //需要编码  安全
            String json=CookieUtils.getCookieValue(request,"TT_CART",true);
            List<CartItem> list=JsonUtils.jsonToList(json,CartItem.class);
           //判断是否为空
            if(list==null){
                return new ArrayList<CartItem>();
            }
            return list;
        }catch (Exception e){
             e.printStackTrace();
             return new ArrayList<CartItem>();
        }
    }


//    删除购物车
    public TaotaoResult deleteCartItem(long itemId,HttpServletRequest request,HttpServletResponse response){
        List<CartItem>  cartItemList=getCartItemList(request);
        for(CartItem item:cartItemList){
            if(item.getId()==itemId){
                cartItemList.remove(item);
                break;
            }
        }
        //   写入cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(cartItemList),Integer.parseInt(COOKIE_EXPIRE),true);
        return TaotaoResult.ok();
    }


}
