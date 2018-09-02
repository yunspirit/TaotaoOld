package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

public interface SearchService {
    //在portal中准备查询
    public SearchResult search(String keyword, int page, int rows);
}
