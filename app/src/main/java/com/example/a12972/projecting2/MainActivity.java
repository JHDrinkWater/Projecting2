package com.example.a12972.projecting2;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a12972.projecting2.adapters.IndicatorAdapter;
import com.example.a12972.projecting2.adapters.MainContentAdapter;
import com.example.a12972.projecting2.utils.LogUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MagicIndicator mMagicIndicator;
    private IndicatorAdapter mIndicatorAdapter;
    private ViewPager mContentPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initEvent();
    }

    private void initEvent() {
        mIndicatorAdapter.setOnIndicatorTapClickListener(new IndicatorAdapter.OnIndicatorTapClickListener() {
            @Override
            public void OnTabClick(int index) {
                LogUtil.d(TAG, "click on item: -----> " + index);
                if (mContentPager != null) {
                    mContentPager.setCurrentItem(index);
                }
            }
        });
    }

    // TODO: 2021/7/19 初始化界面
    private void initView() {
        mMagicIndicator = findViewById(R.id.magic_indicator3);

        //创建Indicator适配器
        mIndicatorAdapter = new IndicatorAdapter(this);

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(mIndicatorAdapter);
        //设置间距自适应平分
        commonNavigator.setAdjustMode(true);

        //创建ViewPager的适配器
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainContentAdapter mainContentAdapter = new MainContentAdapter(fragmentManager);

        //设置ViewPager
        mContentPager = findViewById(R.id.content_pager);
        mContentPager.setAdapter(mainContentAdapter);

        //把ViewPager和Indicator关联起来
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mContentPager);
    }

}
