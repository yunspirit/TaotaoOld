package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import java.awt.*;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    //portal里面商品详情页面是需要商品的详细信息
    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable long itemId,Model model){
        TbItem item=itemService.getItemById(itemId);
        model.addAttribute("item",item);
        return "item";
    }

    //portal里面商品详情页面是需要商品的详细信息
    @RequestMapping(value = "/item/desc/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")

    public String getItemDesc(@PathVariable long itemId){
          return itemService.getItemDescById(itemId);
    }

    //portal里面商品详情页面是需要商品的详细信息
    @RequestMapping(value = "/item/param/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemParamById(@PathVariable long itemId){
           return itemService.getItemParamById(itemId);
    }
}
