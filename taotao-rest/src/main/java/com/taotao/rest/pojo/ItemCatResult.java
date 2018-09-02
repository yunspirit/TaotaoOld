package com.taotao.rest.pojo;

import java.util.List;

// data属性就是CatNode  ItemCatResult才是最终返回的结果
public class ItemCatResult {
    private List data;

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
