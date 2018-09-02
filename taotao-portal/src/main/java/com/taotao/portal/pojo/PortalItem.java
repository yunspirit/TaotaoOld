package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

//在item.jsp页面中  需要Images属性
public class PortalItem extends TbItem {
    public String[] getImages(){
        String images=this.getImage();
        if(images!=null && !images.equals("")){
               String [] imgs=images.split(",");
               return imgs;
        }
        return null;
    }
}
