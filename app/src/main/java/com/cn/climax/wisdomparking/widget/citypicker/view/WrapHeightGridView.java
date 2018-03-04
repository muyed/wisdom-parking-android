package com.cn.climax.wisdomparking.widget.citypicker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * author：leo on 2017/3/17 14:30
 * email： leocheung4ever@gmail.com
 * description: 自适应高度
 * what & why is modified:
 */

public class WrapHeightGridView extends GridView {

    public WrapHeightGridView(Context context) {
        this(context, null);
    }

    public WrapHeightGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapHeightGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
