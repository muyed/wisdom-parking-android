package com.cn.climax.wisdomparking.data.local;

import java.io.Serializable;

/**
 * author：leo on 2018/3/5 0005 02:24
 * email： leocheung4ever@gmail.com
 * description: TODO
 * what & why is modified:
 */
public class BaseLocalBean implements Serializable {

    private int payIconId;
    private String payName;
    private boolean isChecked = false;
    private int payWay;
    private String paySubtitle;

    public String getPaySubtitle() {
        return paySubtitle;
    }

    public void setPaySubtitle(String paySubtitle) {
        this.paySubtitle = paySubtitle;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public int getPayIconId() {
        return payIconId;
    }

    public void setPayIconId(int payIconId) {
        this.payIconId = payIconId;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
