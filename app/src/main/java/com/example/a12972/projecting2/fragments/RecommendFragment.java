package com.example.a12972.projecting2.fragments;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a12972.projecting2.R;
import com.example.a12972.projecting2.adapters.RecommendListAdapter;
import com.example.a12972.projecting2.base.BaseFragment;
import com.example.a12972.projecting2.interfaces.IOnRetryListener;
import com.example.a12972.projecting2.interfaces.IRecommendViewCallBack;
import com.example.a12972.projecting2.presenters.RecommendPresenter;
import com.example.a12972.projecting2.views.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class RecommendFragment extends BaseFragment implements IRecommendViewCallBack,IOnRetryListener {
    private static final String TAG = "RecommendFragment";
    private RecyclerView mRecommendRv;
    private RecommendListAdapter mRecommendListAdapter;
    private RecommendPresenter mRecommendPresenter;
    private UILoader mUiLoader;

    @Override
    protected View OnSubViewLoaded(final LayoutInflater inflater, final ViewGroup container) {

        //设置UI加载器
        mUiLoader = new UILoader(getContext()) {
            @Override
            protected View getSuccessView(ViewGroup group) {
                return createSuccessView(inflater, group);
            }
        };
        mUiLoader.setOnRetryListener(this);

        //获取逻辑层对象
        mRecommendPresenter = RecommendPresenter.getInstance();
        mRecommendPresenter.getRecommendList();
        //设置接口的注册
        mRecommendPresenter.registViewCallBack(this);

        if(mUiLoader.getParent() instanceof ViewGroup){
            ((ViewGroup)mUiLoader.getParent()).removeView(mUiLoader);
        }
        //返回view
        return mUiLoader;
    }

    private View createSuccessView(LayoutInflater inflater, ViewGroup container) {
        //view加载完成
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        //RecyclerView配置
        mRecommendRv = view.findViewById(R.id.recommend_list);
        //线性管理器
        LinearLayoutManager linearLayoutManger = new LinearLayoutManager(getContext());
        linearLayoutManger.setOrientation(LinearLayout.VERTICAL);
        mRecommendRv.setLayoutManager(linearLayoutManger);
        //适配器
        mRecommendListAdapter = new RecommendListAdapter();
        mRecommendRv.setAdapter(mRecommendListAdapter);
        //设置上下间距
        mRecommendRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5);
                outRect.top = UIUtil.dip2px(view.getContext(), 5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //用于删除回调，避免内存泄漏
        if (mRecommendPresenter != null) {
            mRecommendPresenter.unRegistViewCallBack(this);
        }

    }


    @Override
    public void onRecommendListLoad(List<Album> result) {
        // TODO: 2021/7/19 将数据设置给适配器 并更新UI
        mRecommendListAdapter.setData(result);
        mUiLoader.update(UILoader.UIStatus.SUCCESS);
    }

    @Override
    public void onNetworkError() {
        mUiLoader.update(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onEmpty() {
        mUiLoader.update(UILoader.UIStatus.EMPTY);
    }

    @Override
    public void onLoading() {
        mUiLoader.update(UILoader.UIStatus.LOADING);
    }


    @Override
    public void onRetryClick() {
        if(mRecommendPresenter != null){
            mRecommendPresenter.getRecommendList();
        }
    }
}
