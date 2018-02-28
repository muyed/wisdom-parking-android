package com.cn.climax.i_carlib.okgo.app.apiUtils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.cn.climax.i_carlib.base.response.BaseData;
import com.cn.climax.i_carlib.logcat.ZLog;
import com.cn.climax.i_carlib.okgo.data.callback.StringDialogCallback;
import com.cn.climax.i_carlib.okgo.http.GsonConvert;
import com.cn.climax.i_carlib.uiframework.sweetalert.SweetAlertDialog;
import com.cn.climax.i_carlib.util.NetworkUtil;
import com.cn.climax.i_carlib.util.TT;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.request.BaseRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * author：leo on 2017/9/15 0015 07:24
 * email： leocheung4ever@gmail.com
 * description: 封装Json回调
 * what & why is modified:
 */
public abstract class WrapJsonBeanCallback<T> extends StringDialogCallback {

    private Activity mCurrentActivity;
    private SweetAlertDialog dialog;

    public WrapJsonBeanCallback(Activity activity) {
        super(activity);
        this.mCurrentActivity = activity;
    }

    protected abstract void onJsonParseException(int code, String msg, Call call);

    protected abstract void onExecuteSuccess(T bean, Call call);

    protected abstract void onExecuteError(Call call, Response response, Exception e);

    @Override
    public void onSuccess(String s, Call call, Response response) {
        String exUrl = call.request().url().url().getPath();
        try {
            TypeToken<BaseData<T>> token = new TypeToken<BaseData<T>>() {
            };
            BaseData<T> mTBean = GsonConvert.fromJson(s, token.getType());
            ZLog.e("linge -> " + exUrl);
            int returnState = mTBean.getReturnState();

            T returnDataBean = mTBean.getReturnData();

            if (returnDataBean instanceof List) {
                if (returnState == 1 && mTBean.getReturnData() != null) {
                    Type genType = getClass().getGenericSuperclass();
                    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                    Type type = params[0];
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("returnData");
                    if (jsonArray.length() > 0) {
                        T mBean = GsonConvert.fromJson(jsonArray + "", type);
                        onExecuteSuccess(mBean, call);
                    } else if (jsonArray.length() == 0) {
                        if (jsonArray != null && !exUrl.contains("allSupplyMsg")
                                && !exUrl.contains("getIdentifyCode") && !exUrl.contains("getSingleSellerInfo")
                                && !exUrl.contains("getVersionInfo") && !exUrl.contains("fetch") && !exUrl.contains("getUserInfo") && !exUrl.contains("goodsInfo")) { //|| !exUrl.contains("oldSupplySellerMsg") || !exUrl.contains("followSupplyMsg")
                            T mBean = GsonConvert.fromJson(jsonArray + "", type);
                            onExecuteSuccess(mBean, call);
                        } else {
                            ToastUtils.showDebug("响应码: " + mTBean.getReturnState() + " 响应值: " + jsonArray);
//                            if (exUrl.contains("getIdentifyCode") || exUrl.contains("getSingleSellerInfo"))
//                                TT.("扫描的二维码无效，请重新扫描");
//                            else {
                                int exCode = mTBean.getReturnState();
                                List exMsg = (List) mTBean.getReturnData();
                                dealJsonParseException(exCode, GsonConvert.toJSONString(exMsg), call, exUrl);
//                            }
                        }
                    } else {
                        int exCode = mTBean.getReturnState();
                        onExecuteSuccess((T) GsonConvert.fromJson(jsonArray + "", type), call);
                        ToastUtils.showDebug("响应码: " + exCode + " 响应值: " + jsonArray + "");
                    }
                }
            } else if (returnDataBean instanceof String) {
                int exCode = mTBean.getReturnState();
                String exMsg = (String) mTBean.getReturnData();
                dealJsonParseException(exCode, exMsg, call, exUrl);
            } else {
                Type genType = getClass().getGenericSuperclass();
                Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                Type type = params[0];

                JSONObject jsonObject = new JSONObject(s).getJSONObject("returnData") == null
                        ?
                        (JSONObject) JSONObject.NULL
                        :
                        new JSONObject(s).getJSONObject("returnData");

                if (returnState == 1 && jsonObject != null) {
                    especialHandling(jsonObject, exUrl);
                    T mBean = GsonConvert.fromJson(jsonObject.toString(), type);
                    onExecuteSuccess(mBean, call);
                } else {
                    ToastUtils.showDebug("响应码: " + mTBean.getReturnState() + " 响应值: " + jsonObject + "");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            dealJsonParseException(1, "服务端异常，请稍候...", call, exUrl);
            ZLog.e("linge : " + exUrl);
            ZLog.i(e.getMessage());
        }
    }

    public void especialHandling(JSONObject jsonObject, String exUrl) {
        ApiHost host = ApiHost.getInstance();
        Log.i("especialHandling", "especialHandling: " + exUrl);
        try {
            String key = "";
            if (exUrl.contains(host.historyOrderR()) || exUrl.contains(host.historyOrderS())) {
                key = "seller_info";
            } else if (exUrl.contains(host.getGoodsListOfCart())) {
                key = "deliver_default_addr_info";
            }
            if (!TextUtils.isEmpty(key)) {
                if (jsonObject.get(key).toString().equals("[]")) {
                    jsonObject.put(key, null);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected boolean setDialogShow() {
        return true;
    }

    protected void dealJsonParseException(int code, String msg, Call call, String exUrl) {
        if (code == -8036 || code == -8022 || exUrl.contains("fetch") || exUrl.contains("getUserInfo") || exUrl.contains("getVersionInfo")) { //登录密码错误/用户信息不存在  
            onJsonParseException(code, msg, call);
        } else {
            Log.i("dealJsonParseException", "dealJsonParseException: ");
//            TT.showShortWarn(msg);
            onJsonParseException(code, msg, call);
        }
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
//        if (!NetworkUtil.isNetworkAvailable(Core.getInstances().getCurrentContext())){
//            TT.showShortError("网络出现问题");
//        }
        onExecuteError(call, response, e);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
//        dialog = new SweetAlertDialog(mCurrentActivity);
//        dialog.setTitleText("请稍候...");
//        dialog.show();
    }

    @Override
    public void onAfter(String s, Exception e) {
        super.onAfter(s, e);
//        dialog.cancel();
    }

    @Override
    public void onCacheError(Call call, Exception e) {
        super.onCacheError(call, e);
//        dialog.cancel();
    }

    @Override
    public void onCacheSuccess(String s, Call call) {
        super.onCacheSuccess(s, call);
//        dialog.cancel();
    }

    @Override
    public String convertSuccess(Response response) throws Exception {
        return super.convertSuccess(response);
    }

}
