package com.taotao.search.pojo;

import java.util.List;

//查询指定参数 返回的结果类型 最后还用TaotaoResult包装一下
public class SearchResult {
    private List<SearchPojo> itemList;
    private long recordCount;
    private int pageCount;
    private int curPage;

    public List<SearchPojo> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchPojo> itemList) {
        this.itemList = itemList;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }
}
