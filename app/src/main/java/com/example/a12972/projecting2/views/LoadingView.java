package com.example.a12972.projecting2.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import kotlin.jvm.Volatile;

public class LoadingView extends AppCompatImageView {

    //旋转角度
    private int rotateDegree = 0;
    private boolean needRotate = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        //绑定到窗口
        needRotate = true;
        super.onAttachedToWindow();
        post(new Runnable() {
            @Override
            public void run() {
                if(needRotate){
                    rotateDegree = (rotateDegree + 30) % 360;
                    invalidate();
                    if(needRotate) {
                        postDelayed(this, 80);
                    }
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        //从窗口解绑
        needRotate = false;
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * rotate 旋转图片
         * 第一个参数是旋转角度
         * 第二参数是旋转的X坐标
         * 第三个参数是旋转的Y坐标
         */
        canvas.rotate(rotateDegree, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
    }
}
