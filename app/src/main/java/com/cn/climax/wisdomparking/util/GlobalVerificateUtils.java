package com.cn.climax.wisdomparking.util;

import android.content.Context;
import android.content.Intent;

import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.wisdomparking.base.Core;
import com.cn.climax.wisdomparking.ui.main.carport.CarportMineActivity;
import com.cn.climax.wisdomparking.ui.main.licenseplate.AddLicensePlateActivity;
import com.cn.climax.wisdomparking.ui.setting.AuthenticateCertActivity;

/**
 * author：leo on 2018/4/13 0013 11:08
 * email： leocheung4ever@gmail.com
 * description: 全局公用的判定工具类
 * what & why is modified:
 */
public class GlobalVerificateUtils {

    private static GlobalVerificateUtils instance = null;

    public static GlobalVerificateUtils getInstance(Context context) {

        if (null == context) new IllegalArgumentException("context can not be null.");
        if (null == instance) {
            instance = new GlobalVerificateUtils();
        }
        return instance;
    }

    public boolean isEnableOption(Context context) {
        if (!GlobalVerificateUtils.getInstance(context).isAuth()) { //如果没有认证 个人认证->小区认证->车位认证->相关业务操作
            context.startActivity(new Intent(context, AuthenticateCertActivity.class));
            return false;
        } else if (!GlobalVerificateUtils.getInstance(context).isAuthCommunity()) {
            context.startActivity(new Intent(context, CarportMineActivity.class));
            return false;
//        } else if (!GlobalVerificateUtils.getInstance(context).isAuthParkingSpace()) {
//            context.startActivity(new Intent(context, AddDeviceActivity.class));
//            return false;
        }else if (!GlobalVerificateUtils.getInstance(context).isAddCarLicense()){
            context.startActivity(new Intent(context, AddLicensePlateActivity.class));
            return false;
        }
        return true;
    }

    public boolean isAuth() {
        return SharedUtil.getInstance(Core.getInstances()).get(ApiParamsKey.IS_AUTH, false);
    }

    public boolean isAuthCommunity() {
        return SharedUtil.getInstance(Core.getInstances()).get(ApiParamsKey.IS_AUTH_COMMUNITY, false);
    }

    public boolean isAuthParkingSpace() {
        return SharedUtil.getInstance(Core.getInstances()).get(ApiParamsKey.IS_AUTH_PARKING_SPACE, false);
    }
    
    public boolean isAddCarLicense() {
        return SharedUtil.getInstance(Core.getInstances()).get(ApiParamsKey.IS_ADD_CAR_LICENSE, false);
    }
}
