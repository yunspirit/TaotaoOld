package com.taotao.search.service.impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;

  //    根据指定参数 在solr中查询关键字  此处queryString是portal传过来的 还需要加工
    public SearchResult search(String queryString, int page, int rows) throws Exception {
        SolrQuery query=new SolrQuery();
        query.setQuery(queryString);
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //设置搜索域  京东都是在标题中设置
        query.set("df","item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
        query.setHighlightSimplePost("</font>");
        //执行查询
        SearchResult result=searchDao.search(query);
//        计算总页数
        long recordCount=result.getRecordCount();
        int pageCount= (int) (recordCount/rows);
        if(recordCount%rows>0) pageCount++;
        result.setRecordCount(recordCount);
        result.setPageCount(pageCount);
        result.setCurPage(page);
        return result;
    }
}
