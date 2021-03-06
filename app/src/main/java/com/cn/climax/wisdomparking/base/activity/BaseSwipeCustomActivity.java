package com.cn.climax.wisdomparking.base.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.cn.climax.i_carlib.okgo.app.AppActivityManager;
import com.cn.climax.i_carlib.uiframework.swipeback.ISwipeBack;
import com.cn.climax.i_carlib.uiframework.swipeback.SwipeBackHelper;
import com.cn.climax.i_carlib.uiframework.swipeback.SwipeBackLayout;
import com.cn.climax.i_carlib.uiframework.swipeback.SwipeUtils;
import com.cn.climax.i_carlib.util.SettingPrefUtil;
import com.cn.climax.i_carlib.util.phone.AndroidUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.util.KeyboardUtil;

import butterknife.BindView;

/**
 * author：leo on 2016/11/23 20:42
 * email： leocheung4ever@gmail.com
 * description: 左滑返回基类 - 顶部非标题
 * what & why is modified:
 */

public abstract class BaseSwipeCustomActivity extends BaseActivity implements ISwipeBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final int VIBRATE_DURATION = 20;

    private SwipeBackHelper mHelper;
    private SwipeBackLayout mSwipeBackLayout;

    protected int mPageIndex = 1;
    protected int mPageSize = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackHelper(this);
        mHelper.onActivityCreate();

        //设置透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            toolbar.setPadding(0, AndroidUtils.getStatusBarHeight(this), 0, 0);
        }

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {

            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                if (isVibrate())
                    vibrate(VIBRATE_DURATION);
            }

            @Override
            public void onScrollOverThreshold() {
                if (isVibrate())
                    vibrate(VIBRATE_DURATION);
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(@IdRes int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        SwipeUtils.convertActivityFromTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    public void finish() {
        super.finish();
        KeyboardUtil.getInstance().hiddenKeyboard(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int mode = SettingPrefUtil.getSwipeBackEdgeMode(this);
        SwipeBackLayout mSwipeBackLayout = mHelper.getSwipeBackLayout();
        switch (mode) {
            case 0:
                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
                break;
            case 1:
                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
                break;
            case 2:
                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_BOTTOM);
                break;
            case 3:
                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppActivityManager.getAppManager().finishActivity(this);
    }

    /**
     * 是否左滑退出震动提示
     * 默认提示
     */
    protected boolean isVibrate() {
        return true;
    }

    /**
     * 是否显示返回按钮
     * 默认显示
     */
    protected boolean isShowNavBack() {
        return true;
    }

    /**
     * 设置toolbar显示标题
     * 默认不显示标题
     */
    protected String isShowHeaderTitle() {
        return "";
    }

    /**
     * 设置toolbar自定义布局Id
     * 默认是退出箭头 + 标题
     */
    private int customLayoutId() {
        return 0;
    }

    private void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }
}
