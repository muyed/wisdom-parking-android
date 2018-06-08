package com.cn.climax.wisdomparking.data.response;

import com.cn.climax.wisdomparking.data.BaseBean;

/**
 * author：leo on 2018/6/6 0006 21:24
 * email： leocheung4ever@gmail.com
 * description: 我的停车单响应对象
 * what & why is modified:
 */
public class MyOrderResponse extends BaseBean<MyOrderResponse> {

    /**
     * sorts : null
     * ranges : null
     * id : 11
     * createTime : 1528267073000
     * modifyTime : 1528267558000
     * userId : 22
     * communityId : 2
     * carportId : 2
     * parkingShareId : 3
     * ticketNum : PT20180606143753183412
     * status : 6
     * appointmentStartTime : 2018-05-18 14:37:00
     * appointmentEndTime : 2018-05-19 00:38:00
     * startTime : null
     * endTime : null
     * payDeadlineTime : 2018-06-06 14:43:53
     * carLicense : 豫A88888
     * phone : 15361896858
     * price : 36
     * parkingFee : 396
     * overdue : null
     * overdueFee : null
     * carportNum : 384-2201
     * carportMeid : 2
     * province : 浙江省
     * city : 杭州市
     * area : 拱墅区
     * openCode : 1300
     */

    private String sorts;
    private String ranges;
    private String id;
    private long createTime;
    private long modifyTime;
    private int userId;
    private int communityId;
    private int carportId;
    private int parkingShareId;
    private String ticketNum;
    private int status;
    private String appointmentStartTime;
    private String appointmentEndTime;
    private String startTime;
    private String endTime;
    private String payDeadlineTime;
    private String carLicense;
    private String phone;
    private double price;
    private double parkingFee;
    private double overdue;
    private double overdueFee;
    private String carportNum;
    private String carportMeid;
    private String province;
    private String city;
    private String area;
    private String openCode;

    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    public String getRanges() {
        return ranges;
    }

    public void setRanges(String ranges) {
        this.ranges = ranges;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public int getCarportId() {
        return carportId;
    }

    public void setCarportId(int carportId) {
        this.carportId = carportId;
    }

    public int getParkingShareId() {
        return parkingShareId;
    }

    public void setParkingShareId(int parkingShareId) {
        this.parkingShareId = parkingShareId;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(String appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    public String getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public void setAppointmentEndTime(String appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPayDeadlineTime() {
        return payDeadlineTime;
    }

    public void setPayDeadlineTime(String payDeadlineTime) {
        this.payDeadlineTime = payDeadlineTime;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(double parkingFee) {
        this.parkingFee = parkingFee;
    }

    public double getOverdue() {
        return overdue;
    }

    public void setOverdue(double overdue) {
        this.overdue = overdue;
    }

    public double getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(double overdueFee) {
        this.overdueFee = overdueFee;
    }

    public String getCarportNum() {
        return carportNum;
    }

    public void setCarportNum(String carportNum) {
        this.carportNum = carportNum;
    }

    public String getCarportMeid() {
        return carportMeid;
    }

    public void setCarportMeid(String carportMeid) {
        this.carportMeid = carportMeid;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }
}
