package com.cn.climax.i_carlib.okgo.app.apiUtils;

import android.content.Intent;
import android.text.TextUtils;

import com.cn.climax.i_carlib.okgo.app.BaseApplication;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.DeleteRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.HeadRequest;
import com.lzy.okgo.request.OptionsRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;

/**
 * Api 接口管理
 */
public class ApiManage {

    /**
     * get请求
     */
    public static GetRequest get(String url) {
        return OkGo.get(ApiHost.ApiUrlHost + url);
    }

    /**
     * post请求
     */
    public static PostRequest post(String url) {
        return OkGo.post(ApiHost.ApiUrlHost + url);
    }

    /**
     * put请求
     */
    public static PutRequest put(String url) {
        return OkGo.put(ApiHost.ApiUrlHost + url);
    }

    /**
     * head请求
     */
    public static HeadRequest head(String url) {
        return OkGo.head(ApiHost.ApiUrlHost + url);
    }

    /**
     * delete请求
     */
    public static DeleteRequest delete(String url) {
        return OkGo.delete(ApiHost.ApiUrlHost + url);
    }

    /**
     * patch请求
     */
    public static OptionsRequest options(String url) {
        return OkGo.options(ApiHost.ApiUrlHost + url);
    }

    /**
     * 取消请求
     */
    public static void cancle(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }

}
