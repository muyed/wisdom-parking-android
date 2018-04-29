package com.cn.climax.wisdomparking.data.response;

import com.cn.climax.wisdomparking.data.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * author：leo on 2018/2/26 22:50
 * email： leocheung4ever@gmail.com
 * description: 登录响应对象
 * what & why is modified:
 */

public class LoginResponse extends BaseBean<LoginResponse> {


    /**
     * realName : 张林
     * accountCashConf : 0.01
     * communityList : [{"addr":"萍水西街我也不知道多少号","area":"拱墅区","carportList":[{"bind":true,"bindCode":"898151","carportNum":"384-2001","communityId":2,"communityModuleId":1,"createTime":null,"id":1,"latitude":234.3464335465,"lockStatus":1,"longitude":23.9826428475,"meid":"1","modifyTime":null,"ranges":null,"shareStatus":0,"sorts":null},{"bind":true,"bindCode":"111111","carportNum":"384-2201","communityId":2,"communityModuleId":2,"createTime":null,"id":2,"latitude":23.9826428475,"lockStatus":1,"longitude":23.9826428444,"meid":"2","modifyTime":null,"ranges":null,"shareStatus":1,"sorts":null},{"bind":true,"bindCode":"222222","carportNum":"344-4444","communityId":2,"communityModuleId":2,"createTime":null,"id":3,"latitude":222.222222222,"lockStatus":1,"longitude":123.33333333,"meid":"3","modifyTime":null,"ranges":null,"shareStatus":0,"sorts":null}],"city":"杭州市","communityId":2,"communityName":"方家花苑","communityType":2,"province":"浙江省","reason":null,"type":1},{"addr":"萍水西街我也不知道多少号","area":"拱墅区","carportList":[{"bind":true,"bindCode":"898151","carportNum":"384-2001","communityId":2,"communityModuleId":1,"createTime":null,"id":1,"latitude":234.3464335465,"lockStatus":1,"longitude":23.9826428475,"meid":"1","modifyTime":null,"ranges":null,"shareStatus":0,"sorts":null},{"bind":true,"bindCode":"111111","carportNum":"384-2201","communityId":2,"communityModuleId":2,"createTime":null,"id":2,"latitude":23.9826428475,"lockStatus":1,"longitude":23.9826428444,"meid":"2","modifyTime":null,"ranges":null,"shareStatus":1,"sorts":null},{"bind":true,"bindCode":"222222","carportNum":"344-4444","communityId":2,"communityModuleId":2,"createTime":null,"id":3,"latitude":222.222222222,"lockStatus":1,"longitude":123.33333333,"meid":"3","modifyTime":null,"ranges":null,"shareStatus":0,"sorts":null}],"city":"杭州市","communityId":2,"communityName":"方家花苑","communityType":2,"province":"浙江省","reason":null,"type":2},{"addr":"萍水西街我也不知道多少号","area":"拱墅区","carportList":[{"bind":true,"bindCode":"898151","carportNum":"384-2001","communityId":2,"communityModuleId":1,"createTime":null,"id":1,"latitude":234.3464335465,"lockStatus":1,"longitude":23.9826428475,"meid":"1","modifyTime":null,"ranges":null,"shareStatus":0,"sorts":null},{"bind":true,"bindCode":"111111","carportNum":"384-2201","communityId":2,"communityModuleId":2,"createTime":null,"id":2,"latitude":23.9826428475,"lockStatus":1,"longitude":23.9826428444,"meid":"2","modifyTime":null,"ranges":null,"shareStatus":1,"sorts":null},{"bind":true,"bindCode":"222222","carportNum":"344-4444","communityId":2,"communityModuleId":2,"createTime":null,"id":3,"latitude":222.222222222,"lockStatus":1,"longitude":123.33333333,"meid":"3","modifyTime":null,"ranges":null,"shareStatus":0,"sorts":null}],"city":"杭州市","communityId":2,"communityName":"方家花苑","communityType":2,"province":"浙江省","reason":null,"type":3}]
     * overdueMultipleConf : 2
     * identityCard : 321282199002211218
     * carportCashConf : 0.01
     * userCarportList : [{"alias":null,"carportId":1,"createTime":1524061004000,"deposit":0.01,"id":2,"modifyTime":null,"parent":null,"payNum":"CD20180418221644213319","ranges":null,"sorts":null,"status":0,"userId":19},{"alias":null,"carportId":2,"createTime":1524061748000,"deposit":0.01,"id":3,"modifyTime":1524061764000,"parent":null,"payNum":"CD20180418222908320695","ranges":null,"sorts":null,"status":1,"userId":19},{"alias":null,"carportId":3,"createTime":1524066339000,"deposit":0.01,"id":4,"modifyTime":null,"parent":null,"payNum":"CD20180418234539356044","ranges":null,"sorts":null,"status":0,"userId":19}]
     * account : null
     * payDeadlineMinConf : 6
     */

    private String realName;
    private double accountCashConf;
    private int overdueMultipleConf;
    private String identityCard;
    private double carportCashConf;
    private String account;
    private int payDeadlineMinConf;
    private List<CommunityListBean> communityList;
    private List<UserCarportListBean> userCarportList;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public double getAccountCashConf() {
        return accountCashConf;
    }

    public void setAccountCashConf(double accountCashConf) {
        this.accountCashConf = accountCashConf;
    }

    public int getOverdueMultipleConf() {
        return overdueMultipleConf;
    }

    public void setOverdueMultipleConf(int overdueMultipleConf) {
        this.overdueMultipleConf = overdueMultipleConf;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public double getCarportCashConf() {
        return carportCashConf;
    }

    public void setCarportCashConf(double carportCashConf) {
        this.carportCashConf = carportCashConf;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getPayDeadlineMinConf() {
        return payDeadlineMinConf;
    }

    public void setPayDeadlineMinConf(int payDeadlineMinConf) {
        this.payDeadlineMinConf = payDeadlineMinConf;
    }

    public List<CommunityListBean> getCommunityList() {
        return communityList;
    }

    public void setCommunityList(List<CommunityListBean> communityList) {
        this.communityList = communityList;
    }

    public List<UserCarportListBean> getUserCarportList() {
        return userCarportList;
    }

    public void setUserCarportList(List<UserCarportListBean> userCarportList) {
        this.userCarportList = userCarportList;
    }

    public static class CommunityListBean implements Serializable{
        /**
         * addr : 萍水西街我也不知道多少号
         * area : 拱墅区
         * carportList : [{"bind":true,"bindCode":"898151","carportNum":"384-2001","communityId":2,"communityModuleId":1,"createTime":null,"id":1,"latitude":234.3464335465,"lockStatus":1,"longitude":23.9826428475,"meid":"1","modifyTime":null,"ranges":null,"shareStatus":0,"sorts":null},{"bind":true,"bindCode":"111111","carportNum":"384-2201","communityId":2,"communityModuleId":2,"createTime":null,"id":2,"latitude":23.9826428475,"lockStatus":1,"longitude":23.9826428444,"meid":"2","modifyTime":null,"ranges":null,"shareStatus":1,"sorts":null},{"bind":true,"bindCode":"222222","carportNum":"344-4444","communityId":2,"communityModuleId":2,"createTime":null,"id":3,"latitude":222.222222222,"lockStatus":1,"longitude":123.33333333,"meid":"3","modifyTime":null,"ranges":null,"shareStatus":0,"sorts":null}]
         * city : 杭州市
         * communityId : 2
         * communityName : 方家花苑
         * communityType : 2
         * province : 浙江省
         * reason : null
         * type : 1
         */

        private String addr;
        private String area;
        private String city;
        private int communityId;
        private String communityName;
        private int communityType;
        private String province;
        private String reason;
        private int type;
        private List<CarportListBean> carportList;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getCommunityId() {
            return communityId;
        }

        public void setCommunityId(int communityId) {
            this.communityId = communityId;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
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

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<CarportListBean> getCarportList() {
            return carportList;
        }

        public void setCarportList(List<CarportListBean> carportList) {
            this.carportList = carportList;
        }

        public static class CarportListBean implements Serializable{
            /**
             * bind : true
             * bindCode : 898151
             * carportNum : 384-2001
             * communityId : 2
             * communityModuleId : 1
             * createTime : null
             * id : 1
             * latitude : 234.3464335465
             * lockStatus : 1
             * longitude : 23.9826428475
             * meid : 1
             * modifyTime : null
             * ranges : null
             * shareStatus : 0
             * sorts : null
             */

            private boolean bind;
            private String bindCode;
            private String carportNum;
            private int communityId;
            private int communityModuleId;
            private String createTime;
            private int id;
            private double latitude;
            private int lockStatus;
            private double longitude;
            private String meid;
            private String modifyTime;
            private String ranges;
            private int shareStatus;
            private String sorts;

            public boolean isBind() {
                return bind;
            }

            public void setBind(boolean bind) {
                this.bind = bind;
            }

            public String getBindCode() {
                return bindCode;
            }

            public void setBindCode(String bindCode) {
                this.bindCode = bindCode;
            }

            public String getCarportNum() {
                return carportNum;
            }

            public void setCarportNum(String carportNum) {
                this.carportNum = carportNum;
            }

            public int getCommunityId() {
                return communityId;
            }

            public void setCommunityId(int communityId) {
                this.communityId = communityId;
            }

            public int getCommunityModuleId() {
                return communityModuleId;
            }

            public void setCommunityModuleId(int communityModuleId) {
                this.communityModuleId = communityModuleId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public int getLockStatus() {
                return lockStatus;
            }

            public void setLockStatus(int lockStatus) {
                this.lockStatus = lockStatus;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public String getMeid() {
                return meid;
            }

            public void setMeid(String meid) {
                this.meid = meid;
            }

            public String getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(String modifyTime) {
                this.modifyTime = modifyTime;
            }

            public String getRanges() {
                return ranges;
            }

            public void setRanges(String ranges) {
                this.ranges = ranges;
            }

            public int getShareStatus() {
                return shareStatus;
            }

            public void setShareStatus(int shareStatus) {
                this.shareStatus = shareStatus;
            }

            public String getSorts() {
                return sorts;
            }

            public void setSorts(String sorts) {
                this.sorts = sorts;
            }
        }
    }

    public static class UserCarportListBean implements Serializable{
        /**
         * alias : null
         * carportId : 1
         * createTime : 1524061004000
         * deposit : 0.01
         * id : 2
         * modifyTime : null
         * parent : null
         * payNum : CD20180418221644213319
         * ranges : null
         * sorts : null
         * status : 0
         * userId : 19
         */

        private String alias;
        private int carportId;
        private long createTime;
        private double deposit;
        private int id;
        private String modifyTime;
        private String parent;
        private String payNum;
        private String ranges;
        private String sorts;
        private int status;
        private int userId;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public int getCarportId() {
            return carportId;
        }

        public void setCarportId(int carportId) {
            this.carportId = carportId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public double getDeposit() {
            return deposit;
        }

        public void setDeposit(double deposit) {
            this.deposit = deposit;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getPayNum() {
            return payNum;
        }

        public void setPayNum(String payNum) {
            this.payNum = payNum;
        }

        public String getRanges() {
            return ranges;
        }

        public void setRanges(String ranges) {
            this.ranges = ranges;
        }

        public String getSorts() {
            return sorts;
        }

        public void setSorts(String sorts) {
            this.sorts = sorts;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
