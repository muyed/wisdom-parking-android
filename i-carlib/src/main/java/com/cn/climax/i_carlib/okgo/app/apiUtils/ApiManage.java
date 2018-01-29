package cn.hs.com.wovencloud.data.apiUtils;

import android.content.Intent;
import android.text.TextUtils;

import com.app.framework.app.BaseApplication;
import com.app.framework.utils.SharedUtil;
import com.app.framework.utils.phone.PhoneInfo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.DeleteRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.HeadRequest;
import com.lzy.okgo.request.OptionsRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;

import cn.hs.com.wovencloud.Core;
import cn.hs.com.wovencloud.ui.purchaser.plus.activity.PublishNeedsActivity;
import cn.hs.com.wovencloud.ui.purchaser.setting.activity.IdentityAuthenticationActivity;
import cn.hs.com.wovencloud.util.AndroidUtils;
import cn.hs.com.wovencloud.util.ContextHolderUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Api 接口管理
 */
public class ApiManage {

    /**
     * get请求
     */
    public static GetRequest get(String url) {
        return OkGo.get(ApiHost.ApiUrlHost + url)
                .params(ApiParamsKey.USER_ID, SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get("user_id"));
    }

    /**
     * post请求
     */
    public static PostRequest post(String url) {

        String passport_id = SharedUtil.getInstance(Core.getInstances().getContext()).get(ApiParamsKey.PASSORT_ID);
        if (TextUtils.isEmpty(passport_id)) {
            SharedUtil.getInstance(Core.getInstances().getContext()).put(ApiParamsKey.PASSORT_ID, ApiParamsKey.PASSORT_ID_DEFAULT);
        }

        String long_id = SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.LONG_ID);
        String lat_id = SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.LAT_ID);

        HttpParams loginParams = new HttpParams();
        HttpParams loginUserIdParams = new HttpParams();
        HttpParams loginLoginUserIdParams = new HttpParams();
        HttpParams loginLoginSellerIdParams = new HttpParams();
        if (TextUtils.isEmpty(SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.LOGIN_SHOP_ID)))
            loginParams = null;
        else if (ApiHost.getInstance().login().equals(url))
            loginUserIdParams = null;
        else
            loginParams.put(ApiParamsKey.LOGIN_SHOP_ID, SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.LOGIN_SHOP_ID));

        if (TextUtils.isEmpty(SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.USER_ID)))
            loginUserIdParams = null;
        else if (ApiHost.getInstance().login().equals(url))
            loginUserIdParams = null;
        else
            loginUserIdParams.put(ApiParamsKey.USER_ID, SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.USER_ID));

        if (TextUtils.isEmpty(SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.LOGIN_USER_ID)))
            loginLoginUserIdParams = null;
        else if (ApiHost.getInstance().login().equals(url))
            loginUserIdParams = null;
        else
            loginLoginUserIdParams.put(ApiParamsKey.LOGIN_USER_ID, SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.LOGIN_USER_ID));

        if (TextUtils.isEmpty(SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.LOGIN_SELLER_ID)))
            loginLoginSellerIdParams = null;
        else if (ApiHost.getInstance().login().equals(url))
            loginUserIdParams = null;
        else
            loginLoginSellerIdParams.put(ApiParamsKey.LOGIN_SELLER_ID, SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.LOGIN_SELLER_ID));

        return OkGo.post(ApiHost.ApiUrlHost + url)
                .params(loginUserIdParams)
                .params(loginLoginUserIdParams)
                .params(loginLoginSellerIdParams)
                .params(loginParams)
                .params(ApiParamsKey.APP_BUILD_VERSION, PhoneInfo.getInstance().getVersionName(BaseApplication.getInstance().getContext()))
                .params(ApiParamsKey.APP_VERSION, PhoneInfo.getInstance().getVersionCode(BaseApplication.getInstance().getContext()) + "")
                .params(ApiParamsKey.UUID_ID, AndroidUtils.getDeviceId(ContextHolderUtil.getContext()))
                .params(ApiParamsKey.PASSORT_ID, SharedUtil.getInstance(Core.getInstances().getContext()).get(ApiParamsKey.PASSORT_ID)) // SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get("passport_id")
                .params(ApiParamsKey.LONG_ID, TextUtils.isEmpty(long_id) ? "0.0" : long_id)
                .params(ApiParamsKey.LAT_ID, TextUtils.isEmpty(lat_id) ? "0.0" : lat_id);
    }

    public static PostRequest postEx(String url) {
        String passport_id = SharedUtil.getInstance(Core.getInstances().getContext()).get(ApiParamsKey.PASSORT_ID);
        if (TextUtils.isEmpty(passport_id)) {
            SharedUtil.getInstance(Core.getInstances().getContext()).put(ApiParamsKey.PASSORT_ID, ApiParamsKey.PASSORT_ID_DEFAULT);
        }
        HttpParams loginShopIdP = new HttpParams();
        if (TextUtils.isEmpty(SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.LOGIN_SHOP_ID)))
            loginShopIdP.put(ApiParamsKey.LOGIN_SHOP_ID, "1");
        else
            loginShopIdP.put(ApiParamsKey.LOGIN_SHOP_ID, SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get(ApiParamsKey.LOGIN_SHOP_ID));
        return OkGo.post(ApiHost.ApiUrlHost + url)
                .params(loginShopIdP)
                .params(ApiParamsKey.PASSORT_ID, SharedUtil.getInstance(Core.getInstances().getContext()).get(ApiParamsKey.PASSORT_ID, ApiParamsKey.PASSORT_ID_DEFAULT));
    }

    /**
     * put请求
     */
    public static PutRequest put(String url) {
        return OkGo.put(ApiHost.ApiUrlHost + url)
                .params(ApiParamsKey.USER_ID, SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get("user_id"));
    }

    /**
     * head请求
     */
    public static HeadRequest head(String url) {
        return OkGo.head(ApiHost.ApiUrlHost + url)
                .params(ApiParamsKey.USER_ID, SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get("user_id"));
    }

    /**
     * delete请求
     */
    public static DeleteRequest delete(String url) {
        return OkGo.delete(ApiHost.ApiUrlHost + url)
                .params(ApiParamsKey.USER_ID, SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get("user_id"));
    }

    /**
     * patch请求
     */
    public static OptionsRequest options(String url) {
        return OkGo.options(ApiHost.ApiUrlHost + url).params(ApiParamsKey.USER_ID, SharedUtil.getInstance(BaseApplication.getInstance().getContext()).get("user_id"));
    }

    /**
     * 取消请求
     */
    public static void cancle(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }

}
