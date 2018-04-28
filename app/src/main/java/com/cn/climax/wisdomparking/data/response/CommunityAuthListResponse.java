package com.cn.climax.wisdomparking.data.response;

import com.cn.climax.i_carlib.okgo.data.AbsJavaBean;

/**
 * author：leo on 2018/4/13 0013 13:08
 * email： leocheung4ever@gmail.com
 * description: 小区列表对象
 * what & why is modified:
 */
public class CommunityListResponse extends AbsJavaBean {


    /**
     * sorts : null
     * ranges : null
     * id : 2
     * createTime : 1519908210000
     * modifyTime : null
     * communityName : 方家花苑
     * type : 2
     * province : 浙江省
     * city : 杭州市
     * area : 拱墅区
     * addr : 萍水西街我也不知道多少号
     */

    private String sorts;
    private String ranges;
    private int id;
    private long createTime;
    private String modifyTime;
    private String communityName;
    private int type;
    private String province;
    private String city;
    private String area;
    private String addr;

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

    public Object getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
