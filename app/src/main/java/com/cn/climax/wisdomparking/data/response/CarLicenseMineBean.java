package com.cn.climax.wisdomparking.data.response;

import com.cn.climax.i_carlib.okgo.data.AbsJavaBean;

/**
 * author：leo on 2018/5/3 0003 19:42
 * email： leocheung4ever@gmail.com
 * description: 我的车牌列表
 * what & why is modified:
 */
public class CarLicenseMineBean extends AbsJavaBean {

    /**
     * sorts : null
     * ranges : null
     * id : 5
     * createTime : 1522738151000
     * modifyTime : null
     * userId : 19
     * license : 苏MGF645
     */

    private String sorts;
    private String ranges;
    private int id;
    private long createTime;
    private String modifyTime;
    private int userId;
    private String license;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
