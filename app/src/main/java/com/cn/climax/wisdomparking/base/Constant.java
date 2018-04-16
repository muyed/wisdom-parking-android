package cn.hs.com.wovencloud.base.global;

import io.rong.imlib.model.Conversation;

/**
 * author:xiongx 2017/9/16.
 */

public class Constant {

    public static final String VISITOR_USER_ID = "4294967298"; //游客user_id
    public static final int RECOMMEND_STORAGE_TYPE = 1; //库存推荐
    public static final int RECOMMEND_POPULAR_TYPE = 0; //精品通货
    public static final int RECOMMEND_OTHER_TYPE = 2; //其他


    public static final int REQUEST_PERSONAL_AUTH_CODE = 102;//个人认证请求码

    public static final int ROLE_SUPPLY = 1;//供应商
    public static final int ROLE_PURCHASER = 2;//采购商

    public static final int REQUEST_IM_FROM_ENQUIRY = 9999; //我要修改价格 请求码
    public static final int REQUEST_IM_FROM_CONFIRM_ORDER = 99999; //下单成功 请求码

    public static int TYPE_LIST = 0; //列表样式 列表
    public static int TYPE_GRID = 1; //列表样式 网格
    public static int REFRESH = 0;
    public static int LOAD = 1;

    public static final int CATEGORY = 3001;//获取分类
    public static final int CATEGORY_SORT = 3002;//获取分类
    public static final int CAMERA = 1001;//申请相机权限
    public static final int READ = 1002;//存储权限

    public static final int DATA = 999;
    public static int PERSONAGE_REQUEST_CODE = 100;//个人信息请求码

    public static final int COMPANY_AUTH_REQUEST_CODE = 101; //公司认证请求码

    public static final int REQUEST_TAKE_PHOTO_CODE = 1;//相机标记

    public static final int REQUEST_PHOTO_FRONT_CODE = 1101;//相机标记

    public static final int REQUEST_PHOTO_REVERES_CODE = 1102;//相机标记

    public static final int REQUEST_PHOTO_ADD = 1103;//添加图片

    public static final int REQUEST_PRODUCT_ADD = 1104;//发布供给  产品编辑请求码
    public static final int REQUEST_PRODUCT_SAVE = 1105;//发布供给  产品添加请求码

    public static final int REQUEST_ADDRESS_CODE = 2000;//选择地址请求

    public static final int REQUEST_TAKE_PHOTO_COMPANY_LOGO_CODE = 0x21; //公司logo请求码
    public static final int REQUEST_TAKE_PHOTO_COMPANY_BUSINESS_CODE = 0x22; //营业执照请求码
    public static final int REQUEST_TAKE_PHOTO_COMPANY_ORGANIZATION_CODE = 0x23; //组织机构代码证请求码
    public static final int REQUEST_TAKE_PHOTO_COMPANY_TAX_CODE = 0x24; //税务登记证请求码
    public static final int REQUEST_TAKE_PHOTO_COMPANY_OPEN_ACCOUNT_CODE = 0x25; //开户许可证请求码
    public static final int REQUEST_TAKE_PHOTO_COMPANY_AUTH_CERTIFICATION_CODE = 0x26; //企业认证授权请求码

    public static final int REQUEST_TAKE_PHOTO_PUBLISH_PURCHSE_CODE = 0x31; //公司logo请求码

    public static final int REQUEST_CIRCLE_HOME_CODE = 0x81;

    public static String LABEL = "label";//label公用tab

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`` 权限管理 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;

    public static Conversation.ConversationType[] conversationTypeList() {
        return new Conversation.ConversationType[]{
                Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP,
                Conversation.ConversationType.DISCUSSION, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.CUSTOMER_SERVICE, Conversation.ConversationType.CHATROOM,
                Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE};
    }

    public static final String APP_URL_MANUAL = "http://www.jzyb2b.com/manual.html";
    public static final String APP_URL_AGREEMENT = "http://www.jzyb2b.com/agreement.html";

    //调用系统分享回调
    public static final int REQUEST_CODE_LOCAL_SHARE = 999;
}
