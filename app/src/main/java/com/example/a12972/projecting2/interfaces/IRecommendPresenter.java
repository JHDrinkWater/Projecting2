package com.example.a12972.projecting2.interfaces;

public interface IRecommendPresenter {
    /**
     * 获取推荐内容
     */
    void getRecommendList();

    /**
     * 注册接口回调
     * @param callBack
     */
    void registViewCallBack(IRecommendViewCallBack callBack);

    /**
     * 取消UI的回调注册
     * @param callBack
     */
    void unRegistViewCallBack(IRecommendViewCallBack callBack);
}
