package com.app.framework.data.callback;

import com.app.framework.http.GsonConvert;
import com.app.framework.utils.toast.ToastUtils;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;


public abstract class JsonCallback<T> extends AbsCallback<T> {

    public final String TAG = "API_Request";

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
//        request.headers("header1", "HeaderValue1")//
//                .params("params1", "ParamsValue1")//
//                .params("token", "3215sdf13ad1f65asd4f3ads1f");
    }


    @Override
    public T convertSuccess(Response response) throws Exception {
        Type genType = getClass().getGenericSuperclass();

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        Type type = params[0];

        JsonReader jsonReader = new JsonReader(response.body().charStream());

        T data = GsonConvert.fromJson(jsonReader, type);
        return data;
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        ToastUtils.show("网络异常,请稍后连接...");
    }
}