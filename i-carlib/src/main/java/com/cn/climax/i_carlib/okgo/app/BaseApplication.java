package com.app.framework.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.app.framework.loger.Loger;

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

    /**
     * 初始化配置
     */
    protected void initConfig() {
        instance = this;
        /** 初始化App开关 AppControl */
        initAppControl();

        /** 获取Log的开启状态 （不开启，app每次重启后默认关闭，）*/
        Loger.init(AppControl.isDeBug_IsShowLog(), AppControl.isShowLogCatCaller());
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
        Loger.d(TAG, "onTerminate");
    }

    /**
     * 低内存的时候执行
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Loger.d(TAG, "onLowMemory");
    }

    /**
     * 在配置改变的时候执行
     *
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Loger.d(TAG, "onTrimMemory");
    }

    /**
     * 在配置改变
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Loger.d(TAG, "onConfigurationChanged");
    }

}
