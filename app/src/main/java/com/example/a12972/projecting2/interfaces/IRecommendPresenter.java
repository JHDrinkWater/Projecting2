package com.example.a12972.projecting2.interfaces;

public interface IRecommendPresenter {
    /**
     * 获取推荐内容
     */
    void getRecommendList(final String key);

    /**
     * 下拉刷新
     */
    void pull2RefreshMore();

    /**
     * 上滑加载更多
     */
    void loadMore();

    /**
     * 注册接口回调
     * @param key
     * @param callBack
     */
    void registViewCallBack(String key,IRecommendViewCallBack callBack);

    /**
     * 取消UI的回调注册
     * @param key
     */
    void unRegistViewCallBack(String key);
}
