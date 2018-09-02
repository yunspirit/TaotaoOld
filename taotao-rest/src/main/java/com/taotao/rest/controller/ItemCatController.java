package com.taotao.rest.controller;

import com.github.pagehelper.util.StringUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;


@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

//    方法二：   自己定义请求URL  发布服务之后  别人调用
    //商城最左边的 商品类别
//    @RequestMapping(value = "/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
//    @ResponseBody
//    public String getItemCatList(String callback ){
//        ItemCatResult result=itemCatService.getItemCatList();
//        //result转换为字符串
//        String json=JsonUtils.objectToJson(result);
//        //没有请求参数
//        if(StringUtils.isBlank(callback)){
//            return json;
//        }
//        //有请求参数
//       return callback+"("+json+");";
//    }
//  方法二：  自己定义请求URL  发布服务之后  别人调用
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object getItemCatlist(String callback){
        ItemCatResult result=itemCatService.getItemCatList();
        if(StringUtils.isBlank(callback)){
            return result;
        }
//        支持jsonp调用
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

}
