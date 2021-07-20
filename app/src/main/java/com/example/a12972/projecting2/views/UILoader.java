package com.example.a12972.projecting2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.a12972.projecting2.R;
import com.example.a12972.projecting2.base.BaseApplication;
import com.example.a12972.projecting2.interfaces.IOnRetryListener;
import com.example.a12972.projecting2.utils.LogUtil;

public abstract class UILoader extends FrameLayout {

    private static final String TAG = "UILoader";
    private View mLoadingView = null;
    private View mSuccessView = null;
    private View mNetWorkErrorView = null;
    private View mEmptyView = null;
    private IOnRetryListener mOnRetryListener = null;

    public enum UIStatus {
        LOADING, SUCCESS, NETWORK_ERROR, EMPTY, NONE
    }

    public UIStatus mCurrentStatus = UIStatus.NONE;

    public UILoader(Context context) {
        this(context, null);
    }

    public UILoader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化UI
     */
    private void init() {
        switchUIByCurrentStatus();
    }

    public void update(UIStatus status){
        mCurrentStatus = status;
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                switchUIByCurrentStatus();
            }
        });
    }

    private void switchUIByCurrentStatus() {
        //加载view
        if (mLoadingView == null) {
            mLoadingView = getLoadingView();
            addView(mLoadingView);
        }
        //根据状态设置是否可见
        mLoadingView.setVisibility(mCurrentStatus == UIStatus.LOADING ? VISIBLE : GONE);

        //加载view
        if (mSuccessView == null) {
            mSuccessView = getSuccessView(this);
            addView(mSuccessView);
        }
        //根据状态设置是否可见
        mSuccessView.setVisibility(mCurrentStatus == UIStatus.SUCCESS ? VISIBLE : GONE);

        //加载view
        if (mNetWorkErrorView == null) {
            mNetWorkErrorView = getNetWorkErrorView();
            addView(mNetWorkErrorView);
        }
        //根据状态设置是否可见
        mNetWorkErrorView.setVisibility(mCurrentStatus == UIStatus.NETWORK_ERROR ? VISIBLE : GONE);

        //加载view
        if (mEmptyView == null) {
            mEmptyView = getEmptyView();
            addView(mEmptyView);
        }
        //根据状态设置是否可见
        mEmptyView.setVisibility(mCurrentStatus == UIStatus.EMPTY ? VISIBLE : GONE);
    }

    private View getEmptyView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty, this, false);
    }

    private View getNetWorkErrorView() {
        View netWorkErrorView = LayoutInflater.from(getContext()).
                inflate(R.layout.fragment_network_error, this, false);
        netWorkErrorView.findViewById(R.id.network_error_icon).
                setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG,"click retry!");
                if (mOnRetryListener != null) {
                    mOnRetryListener.onRetryClick();
                }
            }
        });
        return netWorkErrorView;
    }

    protected abstract View getSuccessView(ViewGroup _container);

    private View getLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading, this, false);
    }

    public void setOnRetryListener(IOnRetryListener retryListener){
        mOnRetryListener = retryListener;
    }


}
