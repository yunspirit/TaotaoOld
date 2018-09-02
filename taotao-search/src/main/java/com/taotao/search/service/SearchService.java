package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;

public interface SearchService {
    //    根据指定参数 在solr中查询关键字  此处queryString是portal传过来的 还需要加工
    public SearchResult search(String queryString,int page,int rows) throws Exception;
}
