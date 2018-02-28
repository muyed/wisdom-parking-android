package com.cn.climax.i_carlib.util;

import android.app.Application;
import android.content.Context;

/**
 * author：leo on 2017/6/6 16:00
 * email： leocheung4ever@gmail.com
 * description: 获取相应的上下文环境对象
 * what & why is modified:
 */

public class ContextHolderUtil {

    private final ContextHolderUtil self = this;

    static Context ApplicationContext;

    /* Public Methods */

    /**
     * 初始化context，如果由于不同机型导致反射获取context失败可以在Application调用此方法
     *
     * @param context
     */
    public static void init(Context context) {
        ApplicationContext = context;
    }

    public static Context getContext() {
        if (ApplicationContext == null) {
            try {
                Application application = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication").invoke(null, (Object[]) null);
                if (application != null) {
                    ApplicationContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Application application = (Application) Class.forName("android.app.AppGlobals")
                        .getMethod("getInitialApplication").invoke(null, (Object[]) null);
                if (application != null) {
                    ApplicationContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new IllegalStateException("ContextHolder is not initialed, it is recommend to init with application context.");
        }
        return ApplicationContext;
    }
}
