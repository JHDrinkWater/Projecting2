package com.example.a12972.projecting2.presenters;

import android.support.annotation.Nullable;

import com.example.a12972.projecting2.interfaces.IRecommendPresenter;
import com.example.a12972.projecting2.interfaces.IRecommendViewCallBack;
import com.example.a12972.projecting2.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.a12972.projecting2.Constants.Constants.RECOMMEND_COUNT;

public class RecommendPresenter implements IRecommendPresenter {

    private static final String TAG = "RecommendPresenter";

    private HashMap<String, IRecommendViewCallBack> mCallBacks = new HashMap<>();

    private RecommendPresenter() {
    }

    ;

    private static volatile RecommendPresenter sRecommendPresenter = null;

    public static RecommendPresenter getInstance() {
        if (sRecommendPresenter == null) {
            synchronized (RecommendPresenter.class) {
                if (sRecommendPresenter == null) {
                    sRecommendPresenter = new RecommendPresenter();
                }
            }
        }
        return sRecommendPresenter;
    }

    @Override
    // TODO: 2021/7/19 获取推荐内容 调用喜马拉雅接口
    public void getRecommendList(final String key) {
        //封装参数
        Map<String, String> map = new HashMap<String, String>();
        //参数代表一次返回多少条
        map.put(DTransferConstants.LIKE_COUNT, RECOMMEND_COUNT + "");

        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
                LogUtil.d(TAG, "current thread is ----> " + Thread.currentThread().getName());
                //获取成功
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    handlerRecommendResult(key,albumList);
                    return;
                }
                LogUtil.d(TAG, "error albumList!");
            }

            @Override
            public void onError(int code, String message) {
                //获取失败
                LogUtil.d(TAG, "error code is: ---> " + code + " error message is ---> " + message);
            }
        });
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registViewCallBack(String key, IRecommendViewCallBack callBack) {
        if (!mCallBacks.containsKey(callBack)) {
            mCallBacks.put(key, callBack);
        }
    }

    @Override
    public void unRegistViewCallBack(String key) {
        if (!mCallBacks.containsKey(key)) {
            mCallBacks.remove(key);
        }
    }


    private void handlerRecommendResult(String key, List<Album> albumList) {
        //通知UI更新
        if (mCallBacks != null && mCallBacks.containsKey(key)) {
            mCallBacks.get(key).onRecommendListLoad(albumList);
        }
    }

}
