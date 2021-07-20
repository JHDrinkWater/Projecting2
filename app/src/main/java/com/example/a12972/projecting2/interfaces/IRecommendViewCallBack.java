package com.example.a12972.projecting2.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

public interface IRecommendViewCallBack {

    /**
     * 获取提交内容的结果
     * @param result
     */
    void onRecommendListLoad(List<Album> result);

    /**
     * 网络错误
     */
    void onNetworkError();

    /**
     * 内容为空
     */
    void onEmpty();

    /**
     * 加载中
     */
    void onLoading();
}
