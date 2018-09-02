package com.taotao.search.dao.impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchPojo;
import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDaoImpl implements SearchDao {
    @Autowired
    private SolrClient solrClient;
    // 根据指定参数 在solr中查询关键字 查询并不完善  还需要在SearchServiceImpl中设置一些参数
    @Override
    public SearchResult search(SolrQuery query) {
        try {
            QueryResponse response= null;
            try {
                response = solrClient.query(query);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SolrDocumentList list=response.getResults();
            List<SearchPojo> itemList=new ArrayList<>();
            for (SolrDocument document:list){
                SearchPojo item=new SearchPojo();
                item.setId((String)document.get("id"));
                item.setCategory_name((String)document.get("item_category_name"));
                item.setImage((String) document.get("item_image"));
                //item.setItem_desc(document.get());
                item.setPrice((Long) document.get("item_price"));
                item.setSell_point((String) document.get("item_sell_point"));
                //取高亮显示  这里只让item_title域高亮显示
                Map<String, Map<String, List<String>>> highlighting=response.getHighlighting();
                List<String> tmpList=highlighting.get(document.get("id")).get("item_title");
                if(tmpList!=null && tmpList.size()>0){
                     String str=tmpList.get(0);
                     item.setTitle(str);
                }else {
                    item.setTitle((String) document.get("item_title"));
                }
                itemList.add(item);
            }
            SearchResult result=new SearchResult();
            result.setItemList(itemList);
            result.setRecordCount(list.getNumFound());
            return result;
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
