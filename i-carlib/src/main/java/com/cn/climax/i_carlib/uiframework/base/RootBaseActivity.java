package com.cn.climax.i_carlib.uiframework.base;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.cn.climax.i_carlib.R;
import com.cn.climax.i_carlib.uiframework.base.manager.AppManager;
import com.cn.climax.i_carlib.util.res.ResourceUtil;
import com.cn.climax.i_carlib.util.SettingPrefUtil;
import com.cn.climax.i_carlib.util.StatusBarUtil;
import com.cn.climax.i_carlib.util.ThemeUtil;


/**
 * author：leo on 2016/11/21 17:09
 * email： leocheung4ever@gmail.com
 * description: 库基类
 * what & why is modified:
 */

public abstract class RootBaseActivity extends MPermissionActivity {

    /**
     * 设置布局
     */
    protected abstract int initContentView();

    /**
     * 是否应用半透明状态栏
     */
    protected abstract boolean isApplyStatusBarTranslucency();

    /**
     * 是否应用颜色状态栏
     */
    protected abstract boolean isApplyStatusBarColor();

    /**
     * 注入Injector
     */
    protected abstract void initInjector();

    /**
     * 初始化UI和监听器
     * @param savedInstanceState
     */
    protected abstract void initUiAndListener(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
        setTranslucentStatus(isApplyStatusBarTranslucency());
        setStatusBarColor(isApplyStatusBarColor());
        initInjector();

        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 设置状态栏颜色
     *
     * @param isApply 是否应用
     */
    private void setStatusBarColor(boolean isApply) {
        if (isApply) {
            StatusBarUtil.setColor(this, ResourceUtil.getThemeColor(this), 0);
//            StatusBarUtil.StatusBarLightMode(this, 1);
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
