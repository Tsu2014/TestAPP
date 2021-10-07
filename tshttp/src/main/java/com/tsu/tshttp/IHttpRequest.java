package com.tsu.tshttp;

/**
 * 请求对象的顶层接口
 */
public interface IHttpRequest {
    void setUrl(String url);
    //请求的数据
    void setData(byte [] bytes);
    void setListener();
    void set2();
    void set3();
}
