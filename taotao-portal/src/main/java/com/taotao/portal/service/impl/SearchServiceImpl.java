package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;
    @Override
    //在portal中准备调用solr查询服务
    public SearchResult search(String keyword, int page, int rows) {
        Map<String,String> params=new HashMap<>();
        params.put("keyword",keyword);
        params.put("page",String.valueOf(page));
        params.put("rows",String.valueOf(rows));

        String json=HttpClientUtil.doGet(SEARCH_BASE_URL,params);
        TaotaoResult taotaoResult=TaotaoResult.formatToPojo(json,SearchResult.class);
        SearchResult searchResult=(SearchResult) taotaoResult.getData();
        return searchResult;
    }
}
