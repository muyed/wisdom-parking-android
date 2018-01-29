package cn.hs.com.wovencloud.data.apiUtils;


import android.app.ActivityManager;
import android.util.Log;

import com.app.framework.utils.SharedUtil;

import cn.hs.com.wovencloud.Core;
import cn.hs.com.wovencloud.util.ContextHolderUtil;

/**
 * api url 接口名
 */
public class ApiHost extends ApiHostBase {

    private static ApiHost instance;
    private String customerList;
    private String deliverAddrList;

    private ApiHost() {

    }

    public static ApiHost getInstance() {
        if (instance == null) {
            instance = new ApiHost();
        }
        return instance;
    }

    private String getApiUrl() {
        String id = SharedUtil.getInstance(ContextHolderUtil.getContext()).get(ApiParamsKey.PASSORT_ID, ApiParamsKey.PASSORT_ID_DEFAULT);
        int passort_id = Integer.parseInt(id);
        String url;
        if (passort_id <= 1000) {
            url = "https://dp.jzyb2b.com/";
        } else if (passort_id >= 5000) {
            url = "https://app.jzyb2b.com/";
        } else {
            url = "https://app.jzyb2b.com/";
        }
        return url;
    }

    /**
     * 正式地址
     */
    @Override
    protected String initApiUrlHost() {
        return getApiUrl() + "Trade";
    }

    /**
     * 测试地址
     * "https://dp.jzyb2b.com/Trade";
     */
    @Override
    protected String initApiUrlHost_test() {
        return "https://dp.jzyb2b.com/Trade";
    }


    //版本更新信息获取
    public String getVersionInfo() {
        return "/Version/getVersionInfo";
    }

    //获取Token
    public String getToken() {
        return "/Chat/getToken";
    }

    //获取短信验证码
    public String getVerifyCode() {
        return "/UserPerson/getVerifyCode";
    }

    //获取邮箱验证码
    public String SendMailCode() {
        return "/UserPerson/SendMailCode";
    }

    //注册
    public String registered() {
        return "/UserPerson/registered";
    }

    //登录
    public String login() {
        return "/UserPerson/login";
    }

    //获取推荐码接口
    public String getIdentifyCode() {
        return "/UserPerson/getIdentifyCode";
    }

    //修改登录密码
    public String modifyPassword() {
        return "/UserPerson/modifyPassword";
    }

    //找回密码
    public String resetPassword() {
        return "/UserPerson/resetPassword";
    }

    //修改个人信息
    public String modifyUserInfo() {
        return "/UserPerson/modifyUserInfo";
    }

    //获取个人信息
    public String getUserInfo() {
        return "/UserPerson/getUserInfo";
    }

    //申请公司认证
    public String sellerAdd() {
        return "/UserPerson/sellerAdd";
    }

    //修改代理商接口
    public String modifySellerInfo() {
        return "/UserPlatform/modifySellerInfo";
    }

    //验证手机号唯一性
    public String verfiyMobile() {
        return "/UserPerson/verfiyMobile";
    }

    //校验验证码
    public String checkVerifyCode() {
        return "/UserPerson/checkVerifyCode";
    }

    //验证邮箱唯一性
    public String verfiyEmail() {
        return "/UserPerson/verfiyEmail";
    }

    //查看申请记录：包括公司认证申请，加入公司申请
    public String getMixApplyList() {
        return "/UserPerson/getMixApplyList";
    }

    //我的公司列表
    public String ownCompernyList() {
        return "/UserPerson/ownCompernyList";
    }

    //查看公司详情
    public String getSingleSellerInfo() {
        return "/UserPerson/getSingleSellerInfo";
    }

    //申请个人认证
    public String applyPersonalAuth() {
        return "/UserPerson/applyPersonalAuth";
    }

    //查看公司个人认证情况
    public String getPersonalAuthInfo() {
        return "/UserPerson/getPersonalAuthInfo";
    }

    //设置默认公司
    public String setDefaultComperny() {
        return "/UserPerson/setDefaultComperny";
    }

    //申请加入公司
    public String joinCompany() {
        return "/UserPerson/joinCompany";
    }

    //我的同事列表
    public String getEmployeeList() {
        return "/UserPerson/getEmployeeList";
    }

    //获取代理商行业与公司类型
    public String getCateAndSellerType() {
        return "/UserPerson/getCateAndSellerType";
    }

    //获取待审核员工列表
    public String employeeToCheckList() {
        return "/UserEmployee/employeeToCheckList";
    }

    //审核员工申请
    public String employeeCheck() {
        return "/UserEmployee/employeeCheck";
    }

    //设置销售权限
    public String setSeller() {
        return "/UserEmployee/setSeller";
    }

    //取消销售权限
    public String unSetSeller() {
        return "/UserEmployee/unSetSeller";
    }

    //员工详情接口
    public String getEmployeeInfo() {
        return "/UserPerson/getEmployeeInfo";
    }

    //员工停用/启用
    public String employeeEnableOrDisable() {
        return "/UserEmployee/employeeEnableOrDisable";
    }

    //编辑员工接口信息
    public String editEmployeeInfo() {
        return "/UserEmployee/editEmployeeInfo";
    }

    //获取个人认证申请列表
    public String getPersonalAuthList() {
        return "/UserAgent/getPersonalAuthList";
    }

    //审核个人认证
    public String verifyPersonalAuth() {
        return "/UserAgent/verifyPersonalAuth";
    }

    //获取会员列表
    public String getMemberList() {
        return "/UserAgent/getMemberList";
    }

    //会员停用/启用
    public String memberEnabelOrDisabel() {
        return "/UserAgent/memberEnabelOrDisabel";
    }

    //获取所有客户列表
    public String fetchSellerList() {
        return "/UserAgent/fetchSellerList";
    }

    //审核客户
    public String sellerCheck() {
        return "/UserAgent/sellerCheck";
    }

    //客户的停用
    public String sellerEnabledDisabled() {
        return "/UserAgent/sellerEnabledDisabled";
    }

    //添加供给
    public String supplyAdd() {
        return "/MarketSupply/supplyAdd";
    }

    //修改供给
    public String supplyModify() {
        return "/MarketSupply/supplyModify";
    }

    //发布供给
    public String supplyPublic() {
        return "/MarketSupply/supplyPublic";
    }

    //删除供给
    public String supplyDelete() {
        return "/MarketSupply/supplyDelete";
    }

    //关闭供给
    public String supplyComplete() {
        return "/MarketSupply/supplyComplete";
    }

    //关注供给
    public String supplyFollow() {
        return "/MarketSupply/supplyFollow";
    }

    //获取修改供给数据
    public String descEditSupply() {
        return "/MarketSupply/descEditSupply";
    }

    //供给详情 采购查看
    public String descShowSupply() {
        return "/MarketSupply/descShowSupply";
    }

    //供给详情供给查看
    public String descShowSupplyS() {
        return "/MarketSupply/descShowSupplyS";
    }

    //供给详情 - 采购商
    public String descShowSupplyR() {
        return "/MarketSupply/descShowSupplyR";
    }

    //添加需求
    public String requestAdd() {
        return "/MarketRequest/requestAdd";
    }

    //报价产品详情 加入进货单
    public String quoteRequestAdd() {
        return "/BookingCart/requestAdd";
    }

    //修改需求
    public String requestModify() {
        return "/MarketRequest/requestModify";
    }

    //发布需求
    public String requestPublic() {
        return "/MarketRequest/requestPublic";
    }

    //重新发布
    public String requestRePublic() {
        return "/MarketRequest/requestRePublic";
    }

    //获取系统分类—逐级显示接口
    public String fetchCate() {
        return "/ProductPlatform/fetchCate";
    }

    //删除需求
    public String requestDelete() {
        return "/MarketRequest/requestDelete";
    }

    //关闭需求
    public String requestComplete() {
        return "/MarketRequest/requestComplete";
    }

    //关注需求
    public String requestFollow() {
        return "/MarketRequest/requestFollow";
    }

    //获取修改需求数据
    public String descEditRequest() {
        return "/MarketRequest/descEditRequest";
    }

    //我的供给
    public String listSupply() {
        return "/MarketSupply/listSupply";
    }


    //保存报价
    public String requestQuoteAdd() {
        return "/MarketRequestquote/requestQuoteAdd";
    }

    //删除报价
    public String requestQuoteDelete() {
        return "/MarketRequestquote/requestQuoteDelete";
    }

    //提交报价
    public String requestQuoteSubmit() {
        return "/MarketRequestquote/requestQuoteSubmit";
    }

    //所有供给
    public String allSupplyMsg() {
        return "/MsgSupply/allSupplyMsg";
    }

    //老客供给
    public String oldSupplySellerMsg() {
        return "/MsgSupply/oldSupplySellerMsg";
    }

    //收藏供给
    public String followSupplyMsg() {
        return "/MsgSupply/followSupplyMsg";
    }

    //需求消息-所有需求
    public String allRequestMsg() {
        return "/MsgRequest/allRequestMsg";
    }

    //需求消息-老客需求
    public String oldRequestSellerMsg() {
        return "/MsgRequest/oldRequestSellerMsg";
    }

    //需求消息-我收藏的需求
    public String followRequestMsg() {
        return "/MsgRequest/followRequestMsg";
    }

    //添加系统分类
    public String addCate() {
        return "/ProductPlatform/addCate";
    }

    //添加系统分类单位
    public String addCateUnit() {
        return "/ProductPlatform/addCateUnit";
    }

    //添加商品
    public String addGoods() {
        return "/ProductSetting/addGoods";
    }

    //快速添加商品
    public String fastAddGoods() {
        return "/ProductSetting/fastAddGoods";
    }

    //编辑商品
    public String editGoodsInfo() {
        return "/ProductSetting/editGoodsInfo";
    }

    //获取商品列表
    public String getGoodsList() {
        return "/ProductSetting/getGoodsList";
    }

    //获取商品详情
    public String getGoodsDetailInfo() {
        return "/ProductSetting/getGoodsDetailInfo";
    }

    //批量修改商品价格、货号
    public String modifyPriceOrGoodsNo() {
        return "/ProductSetting/modifyPriceOrGoodsNo";
    }

    //停用/启用 商品
    public String disableGoods() {
        return "/ProductSetting/disableGoods";
    }

    //删除商品
    public String deleteGoods() {
        return "/ProductSetting/deleteGoods";
    }

    //设置取消热销商品
    public String setHostGoods() {
        return "/ProductSetting/setHostGoods";
    }

    //删除商品图片
    public String deleteGoodsPic() {
        return "/ProductSetting/deleteGoodsPic";
    }

    //删除商品规格
    public String deleteGoodsStd() {
        return "/ProductSetting/deleteGoodsStd";
    }

    //代理商获取所有商品列表
    public String getListOfGoodsPublish() {
        return "/ProductAgent/getListOfGoodsPublish";
    }

    //商品上架/下架

    public String goodsOnOrOffShelf() {
        return "/ProductSite/goodsOnOrOffShelf";
    }

    //获取平台商品库（已通过审核发布的商品）
    public String getGoodsPublish() {
        return "/ProductSite/getGoodsPublish";
    }

    //发布商品
    public String goodsPublish() {
        return "/ProductSite/goodsPublish";
    }

    //取消发布商品
    public String goodsPublishCancel() {
        return "/ProductSite/goodsPublishCancel";
    }

    //添加商品、发布供需，获取公司经营类目2三级接口
    public String getCateOfSeller() {
        return "/ProductSetting/getCateOfSeller";
    }

    //我的需求
    public String listRequest() {
        return "/MarketRequest/listRequest";
    }

    /* 我的报价*/
    public String listQuote() {
        return "/MarketRequestquote/listQuote";
    }

    /*获取心愿单列表*/
    public String getGoodsListOfInquiry() {
        return "/Chatofbuyer/getGoodsListOfInquiry";
    }

    /*获取客户列表-供应商*/
    public String getCustomerList() {
        return "/UserPerson/getCustomerList";
    }

    //获取客户列表-采购商
    public String getCustomerListp() {
        return "/UserPerson/getCustomerListp";
    }

    /*心愿单列表*/
    public String listSellerGoods() {
        return "/BookingInquiry/listSellerGoods";
    }

    /*订单列表已购*/
    public String alreadyListSellerGoods() {
        return "/BookingPorder/listSellerGoods";
    }

    /*获取心愿单规格*/
    public String listSellerGoodsStd() {
        return "/BookingInquiry/listSellerGoodsStd";

    }

    //获取进货单列表
    public String BookingCart() {
        return "/BookingCart/listSellerGoods";
    }

    /*获取配送地址*/
    public String getDeliverAddrList() {
        return "/Chat/getDeliverAddrList";
    }

    /*设置默认地址*/
    public String setDefaultOrderAddress() {
        return "/BookingPorder/setDefaultOrderAddress";
    }

    /*添加地址*/
    public String addOrderAddress() {
        return "/BookingPorder/addOrderAddress";
    }

    /*删除地址*/
    public String delAddress() {
        return "/BookingPorder/delAddress";
    }

    //编辑地址
    public String editAddress() {
        return "/BookingPorder/editAddress";
    }

    /*报价详情*/
    public String BookingQuote() {
        return "/BookingQuote/goodsInfo";
    }

    /*供应商未完成订单*/
    public String unFinishedOrderS() {
        return "/BookingPorder/unFinishedOrderS";
    }

    /*供应商完成订单*/
    public String finishedOrderS() {
        return "/BookingPorder/finishedOrderS";
    }

    /*供应商历史订单*/
    public static String historyOrderS() {
        return "/BookingPorder/historyOrderS";
    }

    /*需求详情*/
    public String descShowRequestS() {
        return "/MarketRequest/descShowRequestS";
    }

    /*报价详情*/
    public String descShowRequestSS() {
        return "/MarketRequest/descShowRequestS";
    }

    //获取商品详情 - 对外
    public String getGoodsDetailInfoPublish() {
        return "/ProductSetting/getPrivateGoodsDetailInfoPublish";
    }

    //获取广告信息
    public String getAdthemeInfo() {
        return "/SiteSetting/getAdthemeInfo";
    }

    //获取广告图文信息列表/详情
    public String getInfoAdFetchall() {
        return "/SiteEditor/getInfoAdFetchall";
    }

    //推荐产品
    public String recommendGoods() {
        return "/MsgSupply/recommendGoods";
    }

    //获取供给推荐列表信息
    public String getSupplyinfo() {
        return "/SiteRecommend/getSupplyinfo";
    }

    //获取需求列表信息
    public String getRequestinfo() {
        return "/SiteRecommend/getRequestinfo";
    }

    //报价选品
    public String selectQuoteGoods() {
        return "/MarketRequestquote/selectQuoteGoods";
    }

    //报价选规格
    public String selectQuoteGoodsStd() {
        return "/MarketRequestquote/selectQuoteGoodsStd";
    }

    //报价提交产品列表
    public String listQuoteGoods() {
        return "/MarketRequestquote/listQuoteGoods";
    }

    //删除心意单
    public String InquiryDelete() {
        return "/BookingInquiry/InquiryDelete";
    }

    //获取待恢复规格
    public String getRestoreGoodsStdList() {
        return "/Chatofbuyer/getRestoreGoodsStdList";
    }

    //恢复规格
    public String restoreGoodsStd() {
        return "/Chatofbuyer/restoreGoodsStd";
    }

    /*更新到心意单  	1:接受报价，2：加入进货单*/
    public String inquiryModify() {
        return "/BookingInquiry/inquiryModify";
    }

    // 加入到进货单
    public String add() {
        return "/BookingCart/add";
    }

    //生成采购订单
    public String BookingPorderAdd() {
        return "/BookingPorder/add";
    }

    //删除进货单
    public String cartDelete() {
        return "/BookingCart/cartDelete";
    }

    //修改进货单
    public String cartModify() {
        return "/BookingCart/cartModify";
    }


    //关注供应商
    public String followSupplier() {
        return "/MarketSupply/followSupplier";
    }

    //关注需求发布者
    public String followPurchaser() {
        return "/MarketRequest/followPurchaser";
    }

    //产品列表添加
    public String goodsInquiry() {
        return "/BookingInquiry/goodsInquiry";
    }

    //订单列表（采未完成）
    public String unFinishedOrderR() {
        return "/BookingPorder/unFinishedOrderR";
    }

    //采购商 订单详情
    public String descOrderR() {
        return "/BookingPorder/descOrderR";
    }

    //订单列表（采历史）
    public static String historyOrderR() {
        return "/BookingPorder/historyOrderR";
    }

    //跟订单
    public String newOrderS() {
        return "/BookingPorder/newOrderS";
    }

    //供应商订单详情
    public String descOrderS() {
        return "/BookingPorder/descOrderS";
    }

    /*供应商确认订单*/
    public String confirmsupply() {
        return "/BookingPorder/confirmsupply";
    }

    //订单列表（采完成）
    public String finishedOrderR() {
        return "/BookingPorder/finishedOrderR";
    }

    //采已完成详情
    public String purchaserOrder() {
        return "/BookingPorder/purchaserOrder";
    }

    //供应已完成
    public String supplyerOrder() {
        return "/BookingPorder/supplyerOrder";
    }


    //供应商统计
    public String listHomeR() {
        return "/MyCount/listHomeR";
    }

    //采购商统计
    public String listHomeS() {
        return "/MyCount/listHomeS";
    }

    //公司订单
    public String listAllPorderS() {
        return "/BookingPorder/listAllPorderS";
    }

    //获取关注买手列表
    public String listFollowPurchaser() {
        return "/MarketRequest/listFollowPurchaser";
    }


    //获取已购规格 2 进货单  1 历史采购
    public String alreadyListSellerGoodsStd() {
        return "/BookingPorder/listSellerGoodsStd";
    }

    //获取消息通知列表
    public String msgList() {
        return "/MarketBussiness/msgList";
    }

    //收藏供应
    public String listFollowSupplier() {
        return "/MarketSupply/listFollowSupplier";
    }

    /*获取公司里所有的商品类目,2三4级*/
    public String getCateOfGoods() {
        return "/ProductSetting/getCateOfGoods";
    }

    //获取报价产品详情
    public String quoteGoodsInfo() {
        return "/MarketRequestquote/quoteGoodsInfo";
    }

    //接受报价
    public String requestInquiry() {
        return "/BookingInquiry/requestInquiry";
    }

    //进货单加入心愿单
    public String Cartinquiry() {
        return "/BookingInquiry/Cartinquiry";
    }


    /* ******************************************* 聊天 ******************************************* */
    //创建群
    public String createChatGroup() {
        return "/Chat/createChatGroup";
    }

    //联系我们接口
    public String contactUs() {
        return "/Chat/contactUs";
    }

    //获取客服群信息接口
    public String getGroupInfoOfService() {
        return "/Chat/getGroupInfoOfService";
    }

    //获取聊天窗口商品基本信息
    public String getGoodsInfoInWindow() {
        return "/Chat/getGoodsInfoInWindow";
    }

    //获取简要个人信息
    public String getUserInfoSimple() {
        return "/Chat/getUserInfoSimple";
    }

    //我要询价
    public String inquiry() {
        return "/Chat/inquiry";
    }

    //获取群信息接口
    public String getChatterInfo() {
        return "/Chat/getChatterInfo";
    }

    //取消订单
    public String porderCancel() {
        return "/BookingPorder/porderCancel";
    }

    //采购商完成订单
    public String porderCompleteR() {
        return "/BookingPorder/porderCompleteR";
    }

    //供应商完成订单
    public String porderCompleteS() {
        return "/BookingPorder/porderCompleteS";
    }

    //获取聊天 进货单
    public String getGoodsListOfCart() {
        return "/Chatofbuyer/getGoodsListOfCart";
    }

    //获取询价商品列表
    public String getGoodsListHasInquiry() {
        return "/Chatofseller/getGoodsListHasInquiry";
    }

    //获取商品柜
    public String getGoodsBox() {
        return "/Chatofseller/getGoodsBox";
    }

    //新增报价单  ---- 商品柜推送
    public String quoteAdd() {
        return "/BookingQuote/quoteAdd";
    }

    //获取推送记录
    public String getGoodsListOfQuote() {
        return "/Chatofseller/getGoodsListOfQuote";
    }

    //设置推送记录不可见
    public String setRecordInvisible() {
        return "/Chatofseller/setRecordInvisible";
    }

    //心愿单商品详情
    public String goodsInfo() {
        return "/BookingInquiry/goodsInfo";
    }

    //采购商求购详情
    public String descShowRequestR() {
        return "/MarketRequest/descShowRequestR";
    }

    //获取客户详情 供应商登录
    public String getCustomerInfo() {
        return "/UserPerson/getCustomerInfo";
    }

    //修改客户备注名 手机号 描述信息 供应商
    public String remarks() {
        return "/UserPerson/remarks";
    }

    //采购商客户详情
    public String getCustomerInfop() {
        return "/UserPerson/getCustomerInfop";
    }

    //修改备注信息 采购商
    public String remarksp() {
        return "/UserPerson/remarksp";
    }

    //im历史订单
    public String getHistoryOrderList() {
        return "/Chat/getHistoryOrderList";
    }

    //获取推荐人数
    public String getInvitationCounts() {
        return "/UserPerson/getInvitationCounts";
    }


    public String getSellerId() {
        return "16777216";
    }

    //成功重新发布  刷新
    public String supplyRePublic() {
        return "/MarketSupply/supplyRePublic";
    }
    
     /* ******************************************* 聊天 ******************************************* */

    //添加店铺
    public String addShop() {
        return "/Trade/SiteSetting/add";
    }

    //获取选中产品
    public String selectSupplyGoods() {
        return "/MarketSupply/selectSupplyGoods";
    }

    //认领客户
    public String pullSupplier() {
        return "/Chat/pullSupplier";
    }

    //获取单位库
    public String getuUnitList() {
        return "/ProductPlatform/getuUnitList";
    }

    ///获取指定类目下的单位接口
    public String getUnitListOfCate() {
        return "/ProductPlatform/getUnitListOfCate";
    }

    //
    public String getGoodsDetailInfoChat() {
        return "/ProductSetting/getGoodsDetailInfoChat";
    }

    //判断是否是同事
    public String isOtherCustomer() {
        return "/MarketRequest/isOtherCustomer";
    }


    // 屏蔽询价（供应商）
    public String isInquiryModify() {
        return "/BookingInquiry/isInquiryModify";
    }

    //设置加星标 供应商登录
    public String setGoodContacts() {
        return "/UserPerson/setGoodContacts";
    }

    //设置加星 采购商登录
    public String setGoodContactsp() {
        return "/UserPerson/setGoodContactsp";
    }

    //聊天推送求购
    public String recommendRequest() {
        return "/ChatRecommend/recommendRequest";
    }

    //求购推送记录
    public String listBookingRecommendRequest() {
        return "/ChatRecommend/listBookingRecommendRequest";
    }

    //我的求购推送记录删除
    public String recommendRequestClose() {
        return "/ChatRecommend/recommendRequestClose";
    }

    //聊天推送供应
    public String recommendSupply() {
        return "/ChatRecommend/recommendSupply";
    }

    //供应推送记录
    public String listBookingRecommendSupply() {
        return "/ChatRecommend/listBookingRecommendSupply";
    }

    //我的供应推送记录删除
    public String recommendSupplyClose() {
        return "/ChatRecommend/recommendSupplyClose";
    }

    //查询订阅消息
    public String subscriptionFetch() {
        return "/Subscription/fetch";
    }

    //消息订阅
    public String subscriptionAdd() {
        return "/Subscription/add";
    }

    //编辑订阅消息
    public String subscriptionEdit() {
        return "/Subscription/edit";
    }

    //获取个人订阅类型接口 
    public String fetchSubstOfUser() {
        return "/Subscription/Fetchsubstofuser";
    }
}

