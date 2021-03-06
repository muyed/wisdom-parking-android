package com.cn.climax.i_carlib.okgo.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.cn.climax.i_carlib.logcat.ZLog;

/**
 * Application
 */
public abstract class BaseApplication extends Application {

    private final String TAG = "BaseApplication";

    private static BaseApplication instance;

    /**
     * 初始化App开关 AppControl
     */
    protected abstract void initAppControl();


    /**
     * 程序创建的时候执行
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.initConfig();
        this.initThirdParty();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 依赖宝的同时在工程中引入了多个第三方jar包，导致调用的方法数超过了android设定的65536个（DEX 64K problem），进而导致dex无法生成，也就无法生成APK文件。
        MultiDex.install(this);
    }

    /**
     * 初始化配置
     */
    protected void initConfig() {
        instance = this;
        /** 初始化App开关 AppControl */
        initAppControl();

        /** 获取Log的开启状态 （不开启，app每次重启后默认关闭，）*/
        ZLog.init(AppControl.isDeBug_IsShowLog());
    }

    /**
     * 初始化第三方
     */
    protected void initThirdParty() {
    }

    /**
     * 当前上下文
     */
    public Context getContext() {
        return instance;
    }

    /**
     * application实例
     */
    public static BaseApplication getInstance() {
        return instance;
    }


    /**
     * 获取当前Activity的Context
     */
    public Context getCurrentContext() {
        return AppActivityManager.getAppManager().currentActivity();
    }

    /**
     * 获取当前Activity
     */
    public Activity getCurrentActivity() {
        return AppActivityManager.getAppManager().currentActivity();
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        ZLog.d(TAG, "onTerminate");
    }

    /**
     * 低内存的时候执行
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ZLog.d(TAG, "onLowMemory");
    }

    /**
     * 在配置改变的时候执行
     *
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ZLog.d(TAG, "onTrimMemory");
    }

    /**
     * 在配置改变
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ZLog.d(TAG, "onConfigurationChanged");
    }
}
