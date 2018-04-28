package com.cn.climax.wisdomparking.data.response;

import com.cn.climax.i_carlib.okgo.data.AbsJavaBean;

import java.util.List;

/**
 * author：leo on 2018/4/19 0019 20:33
 * email： leocheung4ever@gmail.com
 * description: TODO
 * what & why is modified:
 */
public class ParkingSpaceMineListBean extends AbsJavaBean {

    private List<ParkingSpaceMineBean> data;

    public List<ParkingSpaceMineBean> getData() {
        return data;
    }

    public void setData(List<ParkingSpaceMineBean> data) {
        this.data = data;
    }
}
