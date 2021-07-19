package com.example.a12972.projecting2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.a12972.projecting2.MainActivity;
import com.example.a12972.projecting2.R;
import com.example.a12972.projecting2.utils.LogUtil;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

public class IndicatorAdapter extends CommonNavigatorAdapter {
    private static final String TAG = "IndicatorAdapter";
    private String[] mTitles;
    private OnIndicatorTapClickListener mOnIndicatorTapClickListener;

    public IndicatorAdapter(Context context) {
        mTitles = context.getResources().getStringArray(R.array.title);
    }

    @Override
    public int getCount() {
        if (mTitles != null) {
            return mTitles.length;
        }
        return 0;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        //创建view
        ColorTransitionPagerTitleView colorTransitionPagerTitleView =
                new ColorTransitionPagerTitleView(context);
        //设置一般情况下的颜色为灰色
        colorTransitionPagerTitleView.setNormalColor(
                Color.parseColor("#aaffffff"));
        //设置选中的情况下的颜色为黑色
        colorTransitionPagerTitleView.setSelectedColor(
                Color.parseColor("#ffffff"));
        //单位sp
        colorTransitionPagerTitleView.setTextSize(20);
        //设置要显示的内容
        colorTransitionPagerTitleView.setText(mTitles[index]);
        //设置title的点击事件,如果点击了title,那么就选中下面的viewPager到对应index
        //也就是说,当我们点击title,下方的viewPager会随着index进行切换
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2021/7/19 点击事件
                LogUtil.d(TAG,"Click item Index is ------> " + index);
                if (mOnIndicatorTapClickListener != null) {
                    mOnIndicatorTapClickListener.OnTabClick(index);
                }
            }
        });
        return colorTransitionPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        linePagerIndicator.setColors(Color.parseColor("#ffffff"));
        return linePagerIndicator;
    }

    public void setOnIndicatorTapClickListener(OnIndicatorTapClickListener onIndicatorTapClickListener){
        mOnIndicatorTapClickListener = onIndicatorTapClickListener;
    }

    public interface OnIndicatorTapClickListener{
        void OnTabClick(int index);
    }
}
