package com.cn.climax.wisdomparking.base.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public interface ORIENTATION {
        int VERTICAL_LEFT = 0;
        int HORIZONTAL_LEFT = 1;
        int VERTICAL_RIGHT = 2;
        int HORIZONTAL_RIGHT = 3;
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    @BindView(R.id.llNavSearchHeader)
//    LinearLayout llNavSearchHeader;

//    @BindView(R.id.allLeftHorizonalOpt)
//    LinearLayout mLeftHorizonalOpt;
//    @BindView(R.id.allLeftVerticalOpt)
//    LinearLayout mLeftVerticalOpt;
    @BindView(R.id.ivLeftHorizonalIcon)
    ImageView ivLeftHorizonalIcon;
    @BindView(R.id.tvLeftHorizonalTitle)
    TextView tvLeftHorizonalTitle;
//    @BindView(R.id.ivLeftVerticalIcon)
//    ImageView ivLeftVerticalIcon;
//    @BindView(R.id.tvLeftVerticalTitle)
//    TextView tvLeftVerticalTitle;

//    @BindView(R.id.allRightHorizonalOpt)
//    LinearLayout mRightHorizonalOpt;
//    @BindView(R.id.allRightVerticalOpt)
//    LinearLayout mRightVerticalOpt;
//    @BindView(R.id.ivRightHorizonalIcon)
//    ImageView ivRightHorizonalIcon;
//    @BindView(R.id.tvRightHorizonalTitle)
//    TextView tvRightHorizonalTitle;
//    @BindView(R.id.ivRightVerticalIcon)
//    ImageView ivRightVerticalIcon;
//    @BindView(R.id.tvRightVerticalTitle)
//    TextView tvRightVerticalTitle;

    private static final int VIBRATE_DURATION = 20;

    private SwipeBackHelper mHelper;
    private SwipeBackLayout mSwipeBackLayout;

    private int rightVertical;
    private boolean isShowRightIcon;
    private boolean isShowRightText;

    private boolean isShowLeftIcon;
    private boolean isShowLeftText;

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

        if (needLeftVerticalOpt(isShowLeftIcon, isShowLeftText))
            setLeftVerticalOpt();
        else {
//            setLeftHorizonalOpt(isShowLeftIcon, isShowLeftText);
            setToolBar(isShowNavBack(), this);
        }
        if (needRightOpt(rightVertical, isShowRightIcon, isShowRightText)) {
            setRightVerticalOpt(isShowRightIcon, isShowRightText);
        } else {
            setRightHorizonalOpt(isShowRightIcon, isShowRightText);
        }
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

    protected void setToolBar(boolean isShowNavBack, final Activity activity) {
        if (!isShowNavBack) {
            ivLeftHorizonalIcon.setVisibility(View.INVISIBLE);
            ivLeftHorizonalIcon.setClickable(false);
            ivLeftHorizonalIcon.setEnabled(false);
            ivLeftHorizonalIcon.setOnClickListener(null);
        } else {
            ivLeftHorizonalIcon.setVisibility(View.VISIBLE);
            ivLeftHorizonalIcon.setClickable(true);
            ivLeftHorizonalIcon.setEnabled(true);
            ivLeftHorizonalIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    KeyboardUtil.getInstance().hiddenKeyboard(activity);
                }
            });
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

    /**
     * 默认带返回的水平布局 显示
     */
    protected boolean needLeftHorizonalOpt(boolean isShowIcon, boolean isShowText) {
        return isShowIcon || isShowText;
    }

    protected boolean needLeftVerticalOpt(boolean isShowIcon, boolean isShowText) {
        return isShowIcon || isShowText;
    }

    /**
     * 默认
     */
    protected boolean needRightHorizonalOpt(int horizontalRight, boolean isShowIcon, boolean isShowText) {
        return horizontalRight == 3 && (isShowIcon || isShowText);
    }

    protected boolean needRightOpt(int verticalRight, boolean isShowIcon, boolean isShowText) {
        this.rightVertical = verticalRight;
        this.isShowRightIcon = isShowIcon;
        this.isShowRightText = isShowText;
        return verticalRight == 2 && (isShowIcon || isShowText);
    }

    /**
     * 设置左边垂直带Icon 相关操作
     */
    private void setLeftVerticalOpt() {
//        mLeftVerticalOpt.setVisibility(View.VISIBLE);
//        ivLeftVerticalIcon.setVisibility(isShowLeftIcon ? View.VISIBLE : View.GONE);
//        tvLeftVerticalTitle.setVisibility(isShowLeftText ? View.VISIBLE : View.GONE);
//        mLeftHorizonalOpt.setVisibility(View.GONE);
    }

    /**
     * 设置左边水平带Icon 相关操作
     */
    private void setLeftHorizonalOpt() {
//        mLeftVerticalOpt.setVisibility(View.GONE);
//        mLeftHorizonalOpt.setVisibility(View.VISIBLE);
//        ivLeftHorizonalIcon.setVisibility(isShowLeftIcon ? View.VISIBLE : View.GONE);
//        tvLeftHorizonalTitle.setVisibility(isShowLeftText ? View.VISIBLE : View.GONE);
    }

    private void setRightVerticalOpt(boolean isShowRightIcon, boolean isShowRightText) {
//        mRightVerticalOpt.setVisibility(View.VISIBLE);
//        ivRightVerticalIcon.setVisibility(isShowRightIcon ? View.VISIBLE : View.GONE);
//        tvRightVerticalTitle.setVisibility(isShowRightText ? View.VISIBLE : View.GONE);
//        mRightHorizonalOpt.setVisibility(View.GONE);
    }

    private void setRightHorizonalOpt(boolean isShowRightIcon, boolean isShowRightText) {
//        mRightVerticalOpt.setVisibility(View.GONE);
//        mRightHorizonalOpt.setVisibility(View.VISIBLE);
//        ivRightHorizonalIcon.setVisibility(isShowRightIcon ? View.VISIBLE : View.GONE);
//        tvRightHorizonalTitle.setVisibility(isShowRightText ? View.VISIBLE : View.GONE);
    }
}
