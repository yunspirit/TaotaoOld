package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
//@RequestMapping("/rest") 不需要写
public class ContentController {
    @Autowired
    ContentService contentService;

    //发布服务  大广告 根据内容分类id查询tb_content表，得到此分类下的内容列表
    @RequestMapping("/content/{cid}")
    @ResponseBody
    public TaotaoResult getContentList(@PathVariable long cid){
        try{
            List<TbContent> list=contentService.getContentList(cid);
            return TaotaoResult.ok(list);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

    //发布服务 大广告 删除redis中失效的缓存数据
    @RequestMapping("/sync/content/{cid}")
    @ResponseBody
    public TaotaoResult synContent(@PathVariable long cid){
        try {
            TaotaoResult result=contentService.synContent(cid);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }
}
