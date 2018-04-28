package com.cn.climax.i_carlib.okgo.app.apiUtils;

import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.utils.HttpUtils;
import com.lzy.okgo.utils.OkLogger;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * author：leo on 2018/4/1 0001 17:56
 * email： leocheung4ever@gmail.com
 * description: TODO
 * what & why is modified:
 */
public class NewPostRequest extends PostRequest {
    
    public NewPostRequest(String url) {
        super(url);
        method = "POST";
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        try {
            headers.put(HttpHeaders.HEAD_KEY_CONTENT_LENGTH, String.valueOf(requestBody.contentLength()));
        } catch (IOException e) {
            OkLogger.e(e);
        }
        Request.Builder requestBuilder = HttpUtils.appendHeaders(headers);
        return requestBuilder.post(requestBody).url(url).tag(tag).build();
    }
}
