package com.cn.climax.i_carlib.okgo.app.apiUtils;


import android.text.TextUtils;

import com.cn.climax.i_carlib.logcat.ZLog;
import com.cn.climax.i_carlib.okgo.app.AppControl;

/**
 *
 * 全局接口域名方法名
 *
 */

public abstract class ApiHostBase {

    private static String TAG = "ApiHostBase";

    /**
     * 服务器Api 正式域名
     *
     * @return
     */
    protected abstract String initApiUrlHost();

    /**
     * 服务器Api 测试域名
     *
     * @return
     */
    protected abstract String initApiUrlHost_test();


    /**
     * 接口域名
     */
    public static String ApiUrlHost = null;



    /**
     * 根据app全局控制开关 设置网络域名
     */
    public void init() {
        if (AppControl.isFormal()) {
            setFormalHost();
        } else {
            seTestHost();
        }
    }

    /**
     * 设置网络为外网【新接口——正式】
     */
    private void setFormalHost() {
        ZLog.d(TAG, "【正式】");

        ApiUrlHost = initApiUrlHost();
    }

    /**
     * 设置网络为【新接口——外网测试】
     */
    private void seTestHost() {
        ZLog.d(TAG, "【外网测试】");

        ApiUrlHost = initApiUrlHost_test();
    }

    /**
     * 接口域名
     */
    public String getApiUrlHost() {
        if (TextUtils.isEmpty(ApiUrlHost)) {
            if (AppControl.isFormal()) {
                setFormalHost();
            } else {
                seTestHost();
            }
        }
        return ApiUrlHost;
    }



}
