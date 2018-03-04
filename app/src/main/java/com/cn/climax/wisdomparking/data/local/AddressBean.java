package com.cn.climax.wisdomparking.data.local;

import java.io.Serializable;

/**
 * author：leo on 2017/3/22 11:00
 * email： leocheung4ever@gmail.com
 * description: 地址实体对象
 * what & why is modified:
 */

public class AddressBean implements Serializable{

    private String addressName;
    private String addressLocation;

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(String addressLocation) {
        this.addressLocation = addressLocation;
    }
}
