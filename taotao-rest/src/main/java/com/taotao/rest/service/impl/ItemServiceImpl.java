package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${ITEM_BASE_INFO_KEY}")
    private String ITEM_BASE_INFO_KEY;
    @Value("${ITEM_DESCRIPTION_KEY}")
    private String ITEM_DESCRIPTION_KEY;
    @Value("${ITEM_PARAM_KEY}")
    private String ITEM_PARAM_KEY;
    @Value("${ITEM_EXPIRE_SECOND}")
    private  int ITEM_EXPIRE_SECOND;
    //portal里面商品详情页面是需要商品的详细信息
    @Override
    public TbItem getItemById(long itemId) {
        StringBuilder sb=new StringBuilder();
        sb.append(REDIS_ITEM_KEY).append(":").append(ITEM_BASE_INFO_KEY).append(":").append(itemId);
//        先查询缓存
        try{
            String json;
            json = jedisClient.get(sb.toString());
            if(StringUtils.isNotBlank(json)){
                TbItem item=JsonUtils.jsonToPojo(json,TbItem.class);
                System.out.println("哈哈 来自缓存");
                return item;
            }
        }catch (Exception e){
             e.printStackTrace();
        }

        //向redis设置缓存 过期时间
        TbItem item= itemMapper.selectByPrimaryKey(itemId);
//        不能影响正常业务逻辑
        try {
            jedisClient.set(sb.toString(),JsonUtils.objectToJson(item));
            //设置过期时间
            jedisClient.expire(sb.toString(),ITEM_EXPIRE_SECOND);
        }catch (Exception e){
           e.printStackTrace();
        }
        return item;
    }

    //portal里面商品详情页面是需要商品的详细信息
    @Override
    public TbItemDesc getItemDescById(long itemId) {

        StringBuilder sb=new StringBuilder();
        sb.append(REDIS_ITEM_KEY).append(":").append(itemId).append(":").append(ITEM_DESCRIPTION_KEY);
//        先查询缓存
        try{
            String json;
            json = jedisClient.get(sb.toString());
            if(StringUtils.isNotBlank(json)){
                TbItemDesc itemDesc=JsonUtils.jsonToPojo(json,TbItemDesc.class);
                System.out.println("哈哈 来自缓存");
                return itemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //向redis设置缓存 过期时间
        TbItemDesc itemDesc= itemDescMapper.selectByPrimaryKey(itemId);
//        不能影响正常业务逻辑
        try {
            jedisClient.set(sb.toString(),JsonUtils.objectToJson(itemDesc));
            //设置过期时间
            jedisClient.expire(sb.toString(),ITEM_EXPIRE_SECOND);
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemDesc;
    }

    //portal里面商品详情页面是需要商品的详细信息
    @Override
    public TbItemParamItem getItemParamById(long itemId) {

        StringBuilder sb=new StringBuilder();
        sb.append(REDIS_ITEM_KEY).append(":").append(itemId).append(":").append(ITEM_PARAM_KEY);
//        先查询缓存
        try{
            String json;
            json = jedisClient.get(sb.toString());
            if(StringUtils.isNotBlank(json)){
                TbItemParamItem itemParamItem=JsonUtils.jsonToPojo(json,TbItemParamItem.class);
                System.out.println("哈哈 来自缓存");
                return itemParamItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbItemParamItemExample example=new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria=example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //向redis设置缓存 过期时间
        List<TbItemParamItem> list= itemParamItemMapper.selectByExampleWithBLOBs(example);
//        不能影响正常业务逻辑
        if(list!=null && list.size()>0){
            TbItemParamItem itemParamItem=list.get(0);
            try {
                jedisClient.set(sb.toString(),JsonUtils.objectToJson(itemParamItem));
                //设置过期时间
                jedisClient.expire(sb.toString(),ITEM_EXPIRE_SECOND);
            }catch (Exception e){
                e.printStackTrace();
            }
            return itemParamItem;
        }
        return null;
    }
}
