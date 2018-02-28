package com.cn.climax.wisdomparking.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.cn.climax.i_carlib.okgo.app.AppActivityManager;
import com.cn.climax.i_carlib.util.SettingPrefUtil;
import com.cn.climax.i_carlib.util.StatusBarUtil;
import com.cn.climax.i_carlib.util.ThemeUtil;
import com.cn.climax.i_carlib.util.res.ResourceUtil;
import com.cn.climax.wisdomparking.R;

/**
 * author：leo on 2016/11/21 17:09
 * email： leocheung4ever@gmail.com
 * description: 库基类
 * what & why is modified:
 */

public abstract class RootBaseActivity extends AppCompatActivity {

    protected Context mContext;

    /**
     * 设置布局
     */
    protected abstract int initContentView();

    protected void setBeforeOnCreateView() {

    }

    /**
     * 是否应用半透明状态栏
     */
    protected boolean isApplyStatusBarTranslucency(){
        return false;
    }

    /**
     * 是否应用颜色状态栏
     */
    protected boolean isApplyStatusBarColor(){
        return false;
    }

    /**
     * 初始化UI和监听器
     */
    protected abstract void initUiAndListener(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initTheme();
        setBeforeOnCreateView();
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
//        if (isSetFilters())
//            getAllViews(this);
        setTranslucentStatus(isApplyStatusBarTranslucency());
        setStatusBarColor(isApplyStatusBarColor());
        mContext = this;
        AppActivityManager.getAppManager().addActivity(this);
    }

    protected boolean isSetFilters() {
        return true;
    }

    //-----------------获取 activity中的所有view  禁止绝大部分EditText输入Emoji表情
//    private void getAllViews(Activity act) {
//        getAllChildViews(act.getWindow().getDecorView());
//    }

//    private void getAllChildViews(View view) {
//        EmojiInputFilter emojiFilter = new EmojiInputFilter();
//        InputFilter[] emojiFilters = {emojiFilter};
//        if (view instanceof ViewGroup) {
//            ViewGroup vp = (ViewGroup) view;
//            for (int i = 0; i < vp.getChildCount(); i++) {
//                View viewchild = vp.getChildAt(i);
//                if (viewchild instanceof EditText) {
//                    ((EditText) viewchild).setFilters(emojiFilters);
//                }
//                //再次 调用本身（递归）
//                getAllChildViews(viewchild);
//            }
//        }
//    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppActivityManager.getAppManager().finishActivity(this);
    }

    /**
     * 设置状态栏颜色
     *
     * @param isApply 是否应用
     */
    private void setStatusBarColor(boolean isApply) {
        if (isApply) {
            StatusBarUtil.setColor(this, ResourceUtil.getThemeColor(this), 0);
        } else {
            setStatusBar();
        }
    }

    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }

    /**
     * 设置半透明状态栏
     *
     * @param isApply 是否应用
     */
    private void setTranslucentStatus(boolean isApply) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (isApply) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    /**
     * 初始化app 主题
     */
    private void initTheme() {
        int theme;
        try {
            theme = getPackageManager().getActivityInfo(getComponentName(), 0).theme;
        } catch (PackageManager.NameNotFoundException e) {
            return;
        }
        if (theme != R.style.AppThemeLaunch) {
            theme = ThemeUtil.themeArr[SettingPrefUtil.getThemeIndex(this)][SettingPrefUtil.getNightModel(this) ? 1 : 0];
        }
        setTheme(theme);
    }

}
