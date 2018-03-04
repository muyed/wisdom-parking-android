package com.cn.climax.wisdomparking.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author：leo on 2017/3/15 16:46
 * email： leocheung4ever@gmail.com
 * description: 可禁止滑动的ViewPager 默认不能滑动
 * what & why is modified:
 */

public class UnScrollableViewPager extends ViewPager {

    private boolean scrollble = true;

    public UnScrollableViewPager(Context context) {
        super(context);
    }

    public UnScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !scrollble || super.onTouchEvent(ev);
    }

    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }
}
