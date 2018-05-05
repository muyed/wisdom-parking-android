package com.cn.climax.wisdomparking.widget.swipemenu;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * author：leo on 2017/9/25 0025 13:15
 * email： leocheung4ever@gmail.com
 * description: 侧滑菜单项 布局
 * what & why is modified:
 */
public class SwipeMenuItemLayout extends ViewGroup {

    /**
     * 滑动Scroller
     */
    private ScrollerCompat mScroller;

    /**
     * 左边界
     */
    private int leftBorder;

    /**
     * 右边界
     */
    private int rightBorder;

    /**
     * 大于该阈值则为滑动
     */
    private int mTouchSlop;

    /**
     * 判断菜单是否显示
     */
    private boolean menuOpen = false;

    private IMenuStatusChangerListener menuStatusChangerListener;

    public SwipeMenuItemLayout(Context context) {
        this(context, null);
    }

    public SwipeMenuItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = ScrollerCompat.create(context);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    private float mDownX;
    private float mMoveX;
    private float mLastMoveX;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mLastMoveX = mDownX;
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = ev.getRawX();
                float diff = Math.abs(mMoveX - mDownX);
                mLastMoveX = mMoveX;
                //当手指拖动值大于TouchSlop值时, 认为应该进行滚动,拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true); //手指点下请求父类不允许控件拦截事件
                mMoveX = event.getRawX();
                int scrollX = (int) (mLastMoveX - mMoveX);
                if (getScrollX() + scrollX < leftBorder) {
                    scrollTo(leftBorder, 0);
                    mLastMoveX = mMoveX;
                    return true;
                } else if (getScrollX() + getWidth() + scrollX > rightBorder) {
                    scrollTo(rightBorder - getWidth(), 0);
                    mLastMoveX = mMoveX;
                    return true;
                }
                scrollBy(scrollX, 0);
                mLastMoveX = mMoveX;
                break;
            case MotionEvent.ACTION_UP:
                int menuWidth = rightBorder - getWidth();
                if (isMenuOpen()) {
                    if (menuWidth - getScrollX() < 50) {
                        setMenuOpen(true);
                    } else {
                        setMenuOpen(false);
                    }
                } else {
                    if (getScrollX() > 50) {
                        setMenuOpen(true);
                    } else {
                        setMenuOpen(false);
                    }
                }
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
        super.computeScroll();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            int childRightWidth = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                //为ScrollerLayout中的每一个子控件在水平方向上进行布局
                childView.layout(childRightWidth, 0, childRightWidth + childView.getMeasuredWidth(), childView.getMeasuredHeight());
                childRightWidth += childView.getMeasuredWidth();
            }
            //初始化左右边界值
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount() - 1).getRight();
        }
    }


    public interface IMenuStatusChangerListener {
        void onMenuStatusChangeListener(boolean isOpen);
    }

    public IMenuStatusChangerListener getMenuStatusChangerListener() {
        return menuStatusChangerListener;
    }

    public void setMenuStatusChangerListener(IMenuStatusChangerListener menuStatusChangerListener) {
        this.menuStatusChangerListener = menuStatusChangerListener;
    }

    public boolean isMenuOpen() {
        return menuOpen;
    }

    public void setMenuOpen(boolean menuOpen) {
        this.menuOpen = menuOpen;
        if (menuOpen) {
            mScroller.startScroll(getScrollX(), 0, rightBorder - getWidth() - getScrollX(), 0);
            invalidate();
        } else {
            mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);
            invalidate();
        }
        if (menuStatusChangerListener != null) {
            menuStatusChangerListener.onMenuStatusChangeListener(isMenuOpen());
        }
    }

    public void setMenuOpenByNoScroll(boolean menuOpen) {
        this.menuOpen = menuOpen;
        if (menuOpen) {
            int menusWidth = rightBorder - getWidth();
            scrollTo(menusWidth, 0);
        } else {
            scrollTo(0, 0);
        }
        if (menuStatusChangerListener != null) {
            menuStatusChangerListener.onMenuStatusChangeListener(isMenuOpen());
        }
    }
}
