package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

public interface ItemService {
    //portal里面商品详情页面是需要商品的详细信息
    public TbItem getItemById(long itemId);
    //portal里面商品详情页面是需要商品的详细信息
    public TbItemDesc getItemDescById(long itemId);
    //portal里面商品详情页面是需要商品的详细信息
    public TbItemParamItem getItemParamById(long itemId);
}
