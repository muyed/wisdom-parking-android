package com.cn.climax.wisdomparking.widget.numberkeyboard;

public class KeyModel {
    private Integer code;
    private String label;
    public KeyModel(Integer code,String lable){
        this.code = code;
        this.label = lable;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLable() {
        return label;
    }

    public void setLable(String lable) {
        this.label = lable;
    }

}
