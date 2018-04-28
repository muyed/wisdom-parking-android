package com.cn.climax.wisdomparking.data.local;

import com.cn.climax.i_carlib.okgo.data.AbsJavaBean;

/**
 * author：leo on 2018/4/13 0013 09:30
 * email： leocheung4ever@gmail.com
 * description: TODO
 * what & why is modified:
 */
public class PoiAddressBean extends AbsJavaBean {

    private String longitude;//经度
    private String latitude;//纬度
    private String text;//信息内容
    public String detailAddress;//详细地址 (搜索的关键字)
    public String province;//省
    public String city;//城市
    public String district;//区域(宝安区)

    public PoiAddressBean(String lon, String lat, String detailAddress, String text, String province, String city, String district) {
        this.longitude = lon;
        this.latitude = lat;
        this.text = text;
        this.detailAddress = detailAddress;
        this.province = province;
        this.city = city;
        this.district = district;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
