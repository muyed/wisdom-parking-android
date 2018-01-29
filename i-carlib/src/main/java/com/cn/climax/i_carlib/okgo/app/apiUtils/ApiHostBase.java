package cn.hs.com.wovencloud.data.apiUtils;


import android.text.TextUtils;

import com.app.framework.app.AppControl;
import com.app.framework.loger.Loger;


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
        Loger.d(TAG, "【正式】");

        ApiUrlHost = initApiUrlHost();
    }

    /**
     * 设置网络为【新接口——外网测试】
     */
    private void seTestHost() {
        Loger.d(TAG, "【外网测试】");

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
