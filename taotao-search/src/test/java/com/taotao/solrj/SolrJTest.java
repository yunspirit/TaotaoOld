package com.taotao.solrj;

import com.taotao.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SolrJTest {
    @Test
    public void testSolrJ()throws Exception{
        //创建连接
        SolrClient solrClient=new HttpSolrClient("http://192.168.25.130:8080/solr/new_core");
        SolrInputDocument document=new SolrInputDocument();
        document.addField("id","solrtest01");
        document.addField("item_title","测试商品");
        document.addField("item_sell_point","卖点");
        solrClient.add(document);
        //提交 SolrServer
        solrClient.commit();
    }

    @Test
    public void testSolrJQuery()throws Exception{
        SolrClient solrServer= new HttpSolrClient("http://192.168.25.130:8080/solr/new_core");
        SolrQuery query=new SolrQuery();
        query.setQuery("*:*");
        QueryResponse response=solrServer.query(query);
        SolrDocumentList list=response.getResults();
        for (SolrDocument solrDocument:list){
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_sell_point"));
        }
    }
    @Test
    public void testSearchItem(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        SearchItemMapper searchItemMapper=ac.getBean(SearchItemMapper.class);
    }

    @Test
    public void testSolrCloud()throws Exception{
//  此处是老版本solr的API 需要更新为5.X
// CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.25.130:2181,192.168.25.130:2182,192.168.25.130:2183");
//        //设置默认collection
//        cloudSolrServer.setDefaultCollection("mycollection1");
//        //以下同单机版solrServer
//        SolrInputDocument document=new SolrInputDocument();
//        document.addField("id","test01");
//        document.addField("item_title","测试商品");
//        document.addField("item_sell_point","卖点");
//        cloudSolrServer.add(document);
//        cloudSolrServer.commit();
        CloudSolrClient cloudSolrClient=new CloudSolrClient("192.168.25.130:2181,192.168.25.130:2182,192.168.25.130:2183");
        cloudSolrClient.setDefaultCollection("mycollection1");
        SolrInputDocument document=new SolrInputDocument();
        document.addField("id","test01");
        cloudSolrClient.add(document);
        cloudSolrClient.commit();
    }
}
