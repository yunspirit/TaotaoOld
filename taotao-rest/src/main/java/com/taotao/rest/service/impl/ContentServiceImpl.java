package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;
    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;
    //发布服务 大广告位置 根据内容分类id查询tb_content表，得到此分类下的内容列表
    //先添加缓存   先查询缓存是否存在
    public List<TbContent> getContentList(long cid){
        //先查缓存
        try {
            String kson=jedisClient.hget(REDIS_CONTENT_KEY,String.valueOf(cid));
            if(!StringUtils.isBlank(kson)){
                List<TbContent> ans=JsonUtils.jsonToList(kson,TbContent.class);
                System.out.println("来自Redis缓存的结果 哈哈哈");
                return ans;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //没有缓存的情况
        TbContentExample  example=new TbContentExample();
        TbContentExample.Criteria criteria=example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list=contentMapper.selectByExampleWithBLOBs(example);

        //返回结果之前先添加缓存
        try{
            //规范key的使用 针对每个项目不同设计key
            //list需要转换成json数据才能缓存
             jedisClient.hset(REDIS_CONTENT_KEY,String.valueOf(cid),JsonUtils.objectToJson(list));
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }


    //发布服务 大广告 删除redis中失效的缓存数据
    @Override
    public TaotaoResult synContent(long cid) {
        try {
            String kson=jedisClient.hget(REDIS_CONTENT_KEY,String.valueOf(cid));
            if(!StringUtils.isBlank(kson)){
                jedisClient.hdel(REDIS_CONTENT_KEY,String.valueOf(cid));
                return TaotaoResult.ok();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
