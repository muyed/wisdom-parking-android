package cn.hs.com.wovencloud.base.me.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.alibaba.android.vlayout.VirtualLayoutManager;

/**
 * author：leo on 2017/3/10 16:01
 * email： leocheung4ever@gmail.com
 * description: 解决RecyclerView滑动阴影
 * what & why is modified:
 */

public class RecyclerViewLayoutManager extends LinearLayoutManager {

    private boolean isScrollVerticallyEnabled = false;
    private boolean isScrollHorizontallyEnabled = false;

    public RecyclerViewLayoutManager(Context context) {
        super(context);
    }

    public RecyclerViewLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public RecyclerViewLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollVerticallyEnabled(boolean flag) {
        this.isScrollVerticallyEnabled = flag;
    }

    public void setScrollHorizontallyEnabled(boolean scrollHorizontallyEnabled) {
        isScrollHorizontallyEnabled = scrollHorizontallyEnabled;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollVerticallyEnabled && super.canScrollVertically();
    }

    @Override
    public boolean canScrollHorizontally() {
        return isScrollHorizontallyEnabled && super.canScrollHorizontally();
    }
}
