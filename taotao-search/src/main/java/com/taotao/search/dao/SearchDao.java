package com.taotao.search.dao;

import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

//对solr的操作 也算作对数据库的操作
public interface SearchDao {
    // 根据指定参数 在solr中查询关键字 查询并不完善  还需要在SearchServiceImpl中设置一些参数
    public SearchResult search(SolrQuery query);
}
