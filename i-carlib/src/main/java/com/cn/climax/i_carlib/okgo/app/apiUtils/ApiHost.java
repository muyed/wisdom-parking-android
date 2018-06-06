package com.cn.climax.i_carlib.okgo.app.apiUtils;


/**
 * api url 接口名
 */
public class ApiHost extends ApiHostBase {

    private static ApiHost instance;

    private ApiHost() {

    }

    public static ApiHost getInstance() {
        if (instance == null) {
            instance = new ApiHost();
        }
        return instance;
    }

    private String getApiUrl() {
        return "https://api.jsppi.com";
    }

    /**
     * 正式地址
     */
    @Override
    protected String initApiUrlHost() {
        return getApiUrl();
    }

    /**
     * 测试地址
     */
    @Override
    protected String initApiUrlHost_test() {
        return getApiUrl();
    }

    //登录
    public String login() {
        return "/api/login";
    }

    //注册获取验证码
    public String getVerifyCodeR() {
        return "/api/reg/sms/";
    }

    //登录获取验证码
    public String getVerifyCodeL() {
        return "/api/login/sms/";
    }

    //注册
    public String register() {
        return "/api/reg";
    }

    //实名认证
    public String idCardAuth() {
        return "/api/user/idCardAuth";
    }
    
    //小区认证
    public String authCommunity() {
        return "/api/community/user/auth";
    }

    //小区列表
    public String getCommunityList() {
        return "/api/community/list";
    }

    //我的小区列表
    public String getMineCommunityList() {
        return "/api/community/user/list";
    }
    
    //支付账户押金
    public String payDeposit() {
        return "/api/pay/wx/";
    }
    
    //微信支付
    public String payCash() {
        return "/api/account/payCash";
    }
    
    //用户绑定车位
    public String bindCarPort() {
        return "/api/carport/bind";
    }
    
    //发布共享单
    public String publishShare() {
        return "/api/share/publish";
    }

    //获取支付宝支付单
    public String getAliPayOrder() {
        return "/api/pay/mayi/";
    }
    
    //获取我的车锁
    public String getMyCarport() {
        return "/api/carport/myCarport";
    }

    //修改车锁昵称
    public String changeCarAlias() {
        return "/api/changeAlias";
    }

    //添加车牌号
    public String addCarLicense() {
        return "/api/carLicense/add";
    }

    //我的车牌列表
    public String getMyCarLicenseList() {
        return "/api/carLicense/myCarLicense";
    }
    
    //删除车牌号
    public String deleteCarLicense() {
        return "/api/carLicense/del/";
    }
    
    //根据指定经纬度查询距离最近的共享单
    public String loadByDistance() {
        return "/api/share/loadByDistance/"; //{longitude}/{latitude}/{limit}
    }
    
    //匹配共享单（创建停车单）
    public String matching() {
        return "/api/ticket/matching"; 
    }
    
    //打开车锁
    public String unLock() {
        return "/api/carport/unLock";  
    }
    
    //银行卡取现
    public String withdrawBalance() {
        return "/api/account/withdrawBalance";
    }
    
    //我的银行卡
    public String myBankList() {
        return "/api/bank/myList";
    }
    
    //添加银行卡
    public String myBankAdd() {
        return "/api/bank/add";
    }
    
    //解绑银行卡
    public String delBank() {
        return "/api/bank/del/";
    }
    
    //获取公告列表
    public String noticeList() {
        return "/api/notice/list";
    }
    
    //我的停车单
    public String myTicket() {
        return "/api/ticket/myTicket";
    }

}

