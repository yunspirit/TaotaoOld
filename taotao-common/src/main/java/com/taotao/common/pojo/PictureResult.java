package com.taotao.common.pojo;


//新增商品时候 插入图片的返回结果
public class PictureResult {
    private int error;
    private String url;
    private String message;
    public int getError() {
        return error;
    }
    public void setError(int error) {
        this.error = error;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
