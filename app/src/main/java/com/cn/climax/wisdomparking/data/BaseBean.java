package com.cn.climax.wisdomparking.data;

import com.cn.climax.i_carlib.base.response.AbsJavaBean;

/**
 * author：leo on 2018/2/24 21:58
 * email： leocheung4ever@gmail.com
 * description: 响应数据基类
 * what & why is modified:
 */

public class BaseBean<T> extends AbsJavaBean {

    private int code;
    private String errMsg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
