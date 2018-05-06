package com.cn.climax.wisdomparking.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.lang.reflect.Field;

/**
 * author：leo on 2018/5/7 0007 02:33
 * email： leocheung4ever@gmail.com
 * description: TODO
 * what & why is modified:
 */
public class AdjustGridView extends GridView {
    
    public AdjustGridView(Context context) {
        super(context);
    }

    public AdjustGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ListAdapter adapter = getAdapter();
        Class<?> gridClass = GridView.class;
        Class<?> absListView = gridClass.getSuperclass();
        int measuredHeight = getMeasuredHeight();
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST && adapter != null) {
            int childCount = adapter.getCount();
            int height = getPaddingTop() + getPaddingBottom();
            int mColumnWidth = 0;
            try {
                Field mListPadding = absListView.getDeclaredField("mListPadding");
                mListPadding.setAccessible(true);
                Rect o = (Rect) mListPadding.get(this);
                height += o.top + o.bottom;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Field columnWidth = gridClass .getDeclaredField("mColumnWidth");
                columnWidth.setAccessible(true);
                mColumnWidth = (int) columnWidth.get(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int singleHeight;
            int numColumns = getNumColumns();

            for (int i = 0; i < childCount; ) {
                singleHeight = 0;
                for (int j = 0; j < numColumns && i < childCount; i++, j++) {
                    View child = adapter.getView(i, null, this);// getChildAt(j);
                    ViewGroup.LayoutParams p = child.getLayoutParams();
                    if (mColumnWidth != 0) {
                        measureChild(child, MeasureSpec.makeMeasureSpec(mColumnWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
                    } else {
                        int width = MeasureSpec.getSize(widthMeasureSpec);
                        int singleWidth = (width - getHorizontalSpacing() * (numColumns - 1)) / numColumns;
                        measureChild(child, MeasureSpec.makeMeasureSpec(singleWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
                    }
                    int measuredHeight1 = child.getMeasuredHeight();
                    singleHeight = Math.max(singleHeight, measuredHeight1);
                }
                height += singleHeight + getVerticalSpacing();
            }
            setMeasuredDimension(getMeasuredWidth(), height > measuredHeight ? height : measuredHeight);
        }
        
    }
}
