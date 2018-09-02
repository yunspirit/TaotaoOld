package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_ITEM_BASE_URL}")
    private String REST_ITEM_BASE_URL;
    @Value("${REST_ITEM_DESC_URL}")
    public String REST_ITEM_DESC_URL;
    @Value("${REST_ITEM_PARAM_URL}")
    private String REST_ITEM_PARAM_URL;

    //    调用服务查询商品信息 基本信息
    @Override
    public TbItem getItemById(long itemId) {
        String json =HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_BASE_URL+itemId);
        if(StringUtils.isNotBlank(json)) System.out.println("yes");
        else System.out.println("no");
        TaotaoResult result=TaotaoResult.formatToPojo(json,PortalItem.class);
        TbItem item=(TbItem) result.getData();
        System.out.println("获取基本信息成功");
        return item;
    }

    //    调用服务查询商品信息 描述信息
    @Override
    public String getItemDescById(long itemId) {
        String json=HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_DESC_URL+itemId);
        TaotaoResult result=TaotaoResult.formatToPojo(json,TbItemDesc.class);
        TbItemDesc desc=(TbItemDesc) result.getData();
        //因为数据库中存的数据一直都是html  不需要进行变化
        String ans=desc.getItemDesc();
        System.out.println("获取描述信息成功");
        return ans;
    }

    //    调用服务查询商品信息 规格参数
    @Override
    public String getItemParamById(long itemId) {
        String json=HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_PARAM_URL+itemId);
        TaotaoResult result=TaotaoResult.formatToPojo(json,TbItemParamItem.class);
        TbItemParamItem paramsss=(TbItemParamItem)result.getData();
        if(paramsss==null) return "";
        String ans=paramsss.getParamData();
        //json转换成java
        List<Map> mapList=JsonUtils.jsonToList(ans,Map.class);
        //数据转换为html
        StringBuffer sb = new StringBuffer();
        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
        sb.append("     <tbody>\n");
        for(Map param:mapList) {
            sb.append("          <tr>\n");
            sb.append("               <th class=\"tdTitle\" colspan=\"2\">"+param.get("group")+"</th>\n");
            sb.append("          </tr>\n");
            //取规格项
            List<Map> object = (List<Map>) param.get("params");
            for (Map map : object) {
                sb.append("          <tr>\n");
                sb.append("               <td class=\"tdTitle\">"+map.get("k")+"</td>\n");
                sb.append("               <td>"+map.get("v")+"</td>\n");
                sb.append("          </tr>\n");
            }
        }
        sb.append("     </tbody>\n");
        sb.append("</table>");
        System.out.println("获取参数信息成功");
        return sb.toString();
    }


}
