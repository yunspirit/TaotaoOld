package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentServiceImpl  implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_URL}")
    private String REST_CONTENT_URL;
    @Value("${REST_CONTENT_AD1_CID}")
    private String REST_CONTENT_AD1_CID;


    //获取大广告位置内容
    public String getAd1tentList() {
        //TaotaoResult类型的json
        String json=HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_URL+REST_CONTENT_AD1_CID);
        //json转换为java对象
        TaotaoResult taotaoResult=TaotaoResult.formatToList(json,TbContent.class);
        //获取data属性
        List<TbContent> contentList=(List)taotaoResult.getData();
        List<AdNode> resultList=new ArrayList<>();
        for (TbContent content:contentList){
           AdNode node=new AdNode();
           //高度 宽度 固定
           node.setHeight(240);
           node.setWidth(670);
           node.setSrc(content.getPic());

           node.setHeightB(240);
           node.setWidth(550);
           node.setSrcB(content.getPic2());

           node.setAlt(content.getTitle());
           node.setHref(content.getUrl());
           resultList.add(node );
        }
        //resulrList转换为json数据
        //可以转换1个pojo  也可以转换List<pojo>
        String resultJson=JsonUtils.objectToJson(resultList);
        return resultJson;
    }
}
