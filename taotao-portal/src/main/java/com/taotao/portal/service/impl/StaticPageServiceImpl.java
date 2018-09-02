package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import com.taotao.portal.service.StaticPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


@Service
public class StaticPageServiceImpl implements StaticPageService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${STATIC_PAGE_PATH}")
    public String STATIC_PAGE_PATH;
//    商品id，根据商品id调用rest发布的服务，获得商品数据
//  （商品基本信息、商品描述、商品规格参数 共3种信息），生成静态页面。返回成功。
    @Override
    public TaotaoResult genItemHtml(long itemId) throws Exception {
        TbItem tbItem=itemService.getItemById(itemId);
        String desc=itemService.getItemDescById(itemId);
        String param=itemService.getItemParamById(itemId);
//      生成静态页面
        Configuration configuration=freeMarkerConfigurer.getConfiguration();
        System.out.println(freeMarkerConfigurer);
        Template template=configuration.getTemplate("item.ftl");
//        创建数据集  需要item
        Map root=new HashMap<>();
        root.put("item",tbItem);
        root.put("itemDesc",desc);
        root.put("itemParam",param);
//        创建一个writer对象
        Writer out=new FileWriter(new File(STATIC_PAGE_PATH+itemId+".html"));
        template.process(root,out);
        out.flush();
        out.close();
        return TaotaoResult.ok();
    }
}
