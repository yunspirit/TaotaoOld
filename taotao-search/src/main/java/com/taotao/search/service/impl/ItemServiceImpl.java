package com.taotao.search.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.pojo.SearchPojo;
import com.taotao.search.service.ItemService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private SolrClient solrClient;
    @Autowired
    private SearchItemMapper searchItemMapper;
    //查询3张表得到SearchPojo数据类型 提交给search层 向solr中添加索引  返回成功的结果
    @Override
    public TaotaoResult importItems() throws Exception {
        List<SearchPojo> list=searchItemMapper.getItemList();
        for (SearchPojo searchPojo:list){
            SolrInputDocument document=new SolrInputDocument();
            document.addField("id",searchPojo.getId());
            document.addField("item_title",searchPojo.getTitle());
            document.addField("item_sell_point",searchPojo.getSell_point());
            document.addField("item_price",searchPojo.getPrice());
            document.addField("item_image",searchPojo.getImage());
            document.addField("item_category_name",searchPojo.getCategory_name());
            document.addField("item_desc",searchPojo.getItem_desc());
            solrClient.add(document);
        }
        //提交
        solrClient.commit();
        System.out.println("上传solr成功");
        return TaotaoResult.ok();
    }
}
