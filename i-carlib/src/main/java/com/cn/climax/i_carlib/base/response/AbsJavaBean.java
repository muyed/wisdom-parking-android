package com.cn.climax.i_carlib.base.response;

import com.cn.climax.i_carlib.okgo.http.GsonConvert;

import java.io.Serializable;


public class AbsJavaBean implements Serializable {

    private static final long serialVersionUID = 0000000000000000000L;

    public String toJSONString() {
        return GsonConvert.toJSONString(this);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + this.toJSONString();
    }


}
