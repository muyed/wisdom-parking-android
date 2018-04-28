package com.cn.climax.wisdomparking.data.local;

import com.cn.climax.i_carlib.okgo.data.AbsJavaBean;

/**
 * author：leo on 2018/4/17 0017 21:36
 * email： leocheung4ever@gmail.com
 * description: 车位锁实体对象
 * what & why is modified:
 */
public class CarLockParkingBean extends AbsJavaBean {
    
    private String lockName;
    private String lockRemark;

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public String getLockRemark() {
        return lockRemark;
    }

    public void setLockRemark(String lockRemark) {
        this.lockRemark = lockRemark;
    }
}
