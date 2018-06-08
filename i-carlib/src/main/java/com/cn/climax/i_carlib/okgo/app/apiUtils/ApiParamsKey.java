package com.cn.climax.i_carlib.okgo.app.apiUtils;


/**
 * 接口Key
 */
public class ApiParamsKey {

    public static final String HOME_APP_KEY = "home_app_key"; //是否点击home键进行app后台隐藏操作
    public static final String RECENT_APP_KEY = "recent_app_key"; //是否点击最近app进行app强制关闭操作

    public static final String IS_AUTH_ALIPAY = "is_auth_alipay"; //阿里支付是否授权成功
    public static final String IS_AUTH_WECHAT = "is_auth_wechat"; //微信支付是否授权成功

    public static final String USER_NAME = "username"; //帐号
    public static final String PHONE = "phone"; //手机号码
    public static final String PASSWORD = "password"; //密码
    public static final String TYPE = "type"; //1:普通用户 2:物业 3:运营人员
    public static final String VERIFY_CODE = "code"; //验证码

    //车位相关
    public static final String CAR_LICENSE = "license"; //车牌号
    public static final String CAR_PORT_ID = "carportId"; //车位锁id
    public static final String CAR_PORT_BIND_CODE = "bindCode"; //车位锁绑定码
    public static final String START_TIME = "startTime"; //共享开始时间 yyyy-MM-dd HH:mm:ss
    public static final String STOP_TIME = "stopTime"; //共享结束时间 yyyy-MM-dd HH:mm:ss
    public static final String SHARE_PRICE = "price"; //价格
    public static final String SHARE_REMARK = "price"; //价格

    public static final String REAL_NAME = "realName"; //真实姓名
    public static final String IDENTITY_CARD = "identityCard"; //身份证
    public static final String IS_AUTH = "is_auth"; //是否认证
    public static final String IS_AUTH_COMMUNITY = "is_auth_community"; //是否小区认证
    public static final String COMMUNITY_BEAN = "communityBean"; //小区实体对象
    public static final String COMMUNITY_ID = "communityId"; //小区ID
    public static final String FLOOR_NO = "floorNo"; //楼号
    public static final String UNIT_NO = "unitNo"; //单元号
    public static final String HOUSE_NO = "houseNo"; //门牌号
    
    public static final String IS_AUTH_PARKING_SPACE = "is_auth_parking_space"; //是否车位认证
    public static final String IS_ADD_CAR_LICENSE = "is_add_car_license"; //是否添加过车牌

    public static final String PAGE_INDEX = "pageNo"; //分页页码
    public static final String PAGE_SIZE = "pageSize"; //分页行数
    
    //共享单
    public static final String PARKING_SHARE_ID = "parkingShareId"; //共享单id
    public static final String APPOINTMENT_START_TIME = "appointmentStartTime"; //预约开始停车时间 yyyy-MM-dd HH:mm:ss
    public static final String APPOINTMENT_STOP_TIME = "appointmentStopTime"; //预约停车结束时间 yyyy-MM-dd HH:mm:ss
    public static final String CAR_LICENSE_NUM = "carLicense"; //预约停车车牌
    
    public static final String ID = "id"; //车锁ID
    
    public static final String BANK_ACCOUNT = "bankAccount"; //银行卡号
    public static final String BANK_ACCOUNT_NAME = "accountName"; //银行卡 - 真实姓名
    public static final String BANK_ACCOUNT_ADDR = "bankAddr"; //银行卡 地址
    public static final String BANK_CODE= "bankCode"; //银行卡号
    public static final String BANK_ID= "bankId"; //银行卡号
    public static final String BANK_WITH_AMOUNT= "amount"; //银行卡号
    
}
