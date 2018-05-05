package com.cn.climax.wisdomparking.widget.swipemenu;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cn.climax.wisdomparking.widget.swipemenu.adapter.SwipeMenuLicenseDeleteAdapter;

/**
 * author：leo on 2017/9/25 0025 13:04
 * email： leocheung4ever@gmail.com
 * description: 自定义侧滑显示菜单的RecyclerView
 * what & why is modified:
 */
public class SwipeMenuRecyclerView extends RecyclerView {

    private Rect mTouchFrame;

    public SwipeMenuRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeMenuRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setMotionEventSplittingEnabled(false); //设置事件分发不允许
    }

    /**
     * 覆写点击事件拦截事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        //获取手指触摸点击的位置
        int x = (int) e.getX();
        int y = (int) e.getY();

        MotionEventCompat.getActionMasked(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                SwipeMenuLicenseDeleteAdapter menuAdapter = (SwipeMenuLicenseDeleteAdapter) getAdapter();
                int itemOpenPosition = menuAdapter.getMenuOpenItemIndex();
                if (itemOpenPosition != -1) {
                    int pos = -1;
                    //通过点击的坐标计算当前的position
                    int mFirstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                    Rect frame = mTouchFrame;
                    if (frame == null) {
                        mTouchFrame = new Rect();
                        frame = mTouchFrame;
                    }
                    final int count = getChildCount();
                    for (int i = count - 1; i >= 0; i--) {
                        final View child = getChildAt(i);
                        if (child.getVisibility() == View.VISIBLE) {
                            child.getHitRect(frame);
                            if (frame.contains(x, y)) {
                                pos = mFirstPosition + i;
                            }
                        }
                    }
                    if (pos != itemOpenPosition) {
                        menuAdapter.closeMenus();
                        return true;
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(e);
    }
}
