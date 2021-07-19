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
     * 加载更多
     * @param result
     */
    void onLoaderMore(List<Album> result);

    /**
     * 刷新
     * @param result
     */
    void onRefershMore(List<Album> result);
}
