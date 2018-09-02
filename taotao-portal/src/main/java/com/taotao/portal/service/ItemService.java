package com.taotao.portal.service;

import com.taotao.pojo.TbItem;

public interface ItemService {
    public TbItem getItemById(long itemId);
    public String getItemDescById(long itemId);
    public String getItemParamById(long itemId);
}
