package com.taotao.search.service;


import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.pojo.SearchPojo;

import java.util.List;

public interface ItemService {
    //查询3张表得到SearchPojo数据类型 提交给search层 向solr中添加索引  返回成功的结果
    public TaotaoResult importItems() throws Exception;
}
