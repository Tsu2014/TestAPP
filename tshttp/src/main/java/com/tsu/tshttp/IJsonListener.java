package com.tsu.tshttp;

/**
 * 回调给应用层的接口
 */
public interface IJsonListener<T> {

    //success
    void onSuccess(T t);
    //failue
    void onFailue();

}
