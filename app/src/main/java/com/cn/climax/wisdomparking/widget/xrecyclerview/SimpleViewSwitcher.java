package com.cn.climax.wisdomparking.widget.xrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * author：leo on 2017/9/17 0017 22:59
 * email： leocheung4ever@gmail.com
 * description: 承放 加载进度View切换 的视图组
 * what & why is modified:
 */
public class SimpleViewSwitcher extends ViewGroup {

    public SimpleViewSwitcher(Context context) {
        super(context);
    }

    public SimpleViewSwitcher(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleViewSwitcher(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = this.getChildCount();
        int maxWidth = 0;
        int maxHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int cw = child.getMeasuredWidth();
             int ch = child.getMeasuredHeight();
            maxWidth = child.getMeasuredWidth();
//            maxHeight = child.getMeasuredHeight();
            maxHeight = ch;
        }
        setMeasuredDimension(maxWidth, maxHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(0, 0, r - l, b - t);

            }
        }
    }

    public void setView(View view) {
        if (this.getChildCount() != 0) {
            this.removeViewAt(0);
        }
        this.addView(view, 0);
    }
}
