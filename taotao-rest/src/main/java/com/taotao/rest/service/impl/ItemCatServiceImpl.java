package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

//发布服务  在首页显示 商品类型
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;
    //根据父类ID递归获取下面所有商品
    @Override
    public ItemCatResult getItemCatList() {
        List catList=getItemCatList(0);
        ItemCatResult result=new ItemCatResult();
        result.setData(catList);
        return result;
    }
    private List getItemCatList(long parentId){
        //根据parentId查询列表
        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria= example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list=itemCatMapper.selectByExample(example);
        List resultList=new ArrayList<>();
        int index=0;
        for (TbItemCat itemCat:list){
            if(index>=14) break;
            //如果是父节点
            if(itemCat.getIsParent()){
                CatNode catNode=new CatNode();
                catNode.setUrl("/products/"+itemCat.getId()+".html");
                //  第一级 节点
                if(itemCat.getParentId()==0){
                    catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
                    //第一级节点不能超过14个
                    index++;
                }else {
                    catNode.setName(itemCat.getName());
                }
                catNode.setItems(getItemCatList(itemCat.getId()));
                resultList.add(catNode);
            }else {
                //如果是叶子结点
                String item="/products/"+itemCat.getId()+".html|"+itemCat.getName();
                resultList.add(item);
            }
        }
        return resultList;
    }
}
