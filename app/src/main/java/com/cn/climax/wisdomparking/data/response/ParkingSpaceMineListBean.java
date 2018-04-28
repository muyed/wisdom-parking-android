package com.cn.climax.wisdomparking.data.response;

import com.cn.climax.i_carlib.okgo.data.AbsJavaBean;

/**
 * author：leo on 2018/4/19 0019 20:33
 * email： leocheung4ever@gmail.com
 * description: TODO
 * what & why is modified:
 */
public class ParkingSpaceMineBean extends AbsJavaBean {

    /**
     * area : 拱墅区
     * parent : null
     * city : 杭州市
     * carportNum : 384-2001
     * userId : 19
     * carportId : 1
     * payNum : CD20180418221644213319
     * province : 浙江省
     * alias : null
     * deposit : 0.01
     * communityName : 方家花苑
     * addr : 萍水西街我也不知道多少号
     * status : 0
     */

    private String area;
    private String parent;
    private String city;
    private String carportNum;
    private int userId;
    private int carportId;
    private String payNum;
    private String province;
    private String alias;
    private double deposit;
    private String communityName;
    private String addr;
    private int status;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCarportNum() {
        return carportNum;
    }

    public void setCarportNum(String carportNum) {
        this.carportNum = carportNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarportId() {
        return carportId;
    }

    public void setCarportId(int carportId) {
        this.carportId = carportId;
    }

    public String getPayNum() {
        return payNum;
    }

    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Object getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
