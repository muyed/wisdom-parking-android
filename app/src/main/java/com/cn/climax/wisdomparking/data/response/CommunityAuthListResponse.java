package com.cn.climax.wisdomparking.data.response;

import com.cn.climax.i_carlib.okgo.data.AbsJavaBean;

import java.io.Serializable;
import java.util.List;

/**
 * author：leo on 2018/4/13 0013 13:08
 * email： leocheung4ever@gmail.com
 * description: 认证小区列表对象
 * what & why is modified:
 */
public class CommunityAuthListResponse extends AbsJavaBean {

    /**
     * area : 拱墅区
     * communityType : 2
     * province : 浙江省
     * city : 杭州市
     * carportList : [{"sorts":null,"ranges":null,"id":1,"createTime":1522727581000,"modifyTime":1523952858000,"communityModuleId":1,"communityId":2,"carportNum":"384-2001","meid":"1","bindCode":"898151","longitude":23.9826428475,"latitude":234.3464335465,"shareStatus":0,"lockStatus":1}]
     * communityName : 方家花苑
     * communityId : 2
     * type : 2
     * addr : 萍水西街我也不知道多少号
     */

    private String area;
    private int communityType;
    private String province;
    private String city;
    private String communityName;
    private int communityId;
    private int type;
    private String addr;
    private List<CarportListBean> carportList;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getCommunityType() {
        return communityType;
    }

    public void setCommunityType(int communityType) {
        this.communityType = communityType;
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

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public List<CarportListBean> getCarportList() {
        return carportList;
    }

    public void setCarportList(List<CarportListBean> carportList) {
        this.carportList = carportList;
    }

    public static class CarportListBean implements Serializable{
        /**
         * sorts : null
         * ranges : null
         * id : 1
         * createTime : 1522727581000
         * modifyTime : 1523952858000
         * communityModuleId : 1
         * communityId : 2
         * carportNum : 384-2001
         * meid : 1
         * bindCode : 898151
         * longitude : 23.9826428475
         * latitude : 234.3464335465
         * shareStatus : 0
         * lockStatus : 1
         */

        private String sorts;
        private String ranges;
        private int id;
        private long createTime;
        private long modifyTime;
        private int communityModuleId;
        private int communityId;
        private String carportNum;
        private String meid;
        private String bindCode;
        private double longitude;
        private double latitude;
        private int shareStatus;
        private int lockStatus;

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

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public int getCommunityModuleId() {
            return communityModuleId;
        }

        public void setCommunityModuleId(int communityModuleId) {
            this.communityModuleId = communityModuleId;
        }

        public int getCommunityId() {
            return communityId;
        }

        public void setCommunityId(int communityId) {
            this.communityId = communityId;
        }

        public String getCarportNum() {
            return carportNum;
        }

        public void setCarportNum(String carportNum) {
            this.carportNum = carportNum;
        }

        public String getMeid() {
            return meid;
        }

        public void setMeid(String meid) {
            this.meid = meid;
        }

        public String getBindCode() {
            return bindCode;
        }

        public void setBindCode(String bindCode) {
            this.bindCode = bindCode;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getShareStatus() {
            return shareStatus;
        }

        public void setShareStatus(int shareStatus) {
            this.shareStatus = shareStatus;
        }

        public int getLockStatus() {
            return lockStatus;
        }

        public void setLockStatus(int lockStatus) {
            this.lockStatus = lockStatus;
        }
    }
}
