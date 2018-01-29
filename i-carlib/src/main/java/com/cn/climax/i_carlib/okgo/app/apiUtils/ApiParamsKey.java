package cn.hs.com.wovencloud.data.apiUtils;


/**
 * 接口Key
 */
public class ApiParamsKey {

    public static final String HOME_APP_KEY = "home_app_key"; //是否点击home键进行app后台隐藏操作
    public static final String RECENT_APP_KEY = "recent_app_key"; //是否点击最近app进行app强制关闭操作

    public static String PASSORT_ID_DEFAULT = "1004"; //通行证号 默认

    public static final String IS_CLEAR_CACHE = "is_clear_cache";
    public static final String IS_TOURIST = "is_tourist";

    public static final String GOODS_PICS_ID = "goods_pics_id";//图片id
    public static final String OPTYPE = "optype";
    public static final String TARGET_ID = "targetId";
    public static final String CHAT_BEAN_FROM_SUPPLIER_PRODUCT = "chat_bean_from_supplier_product";
    public static final String CHAT_BEAN_FROM_PURCHASER_PRODUCT = "chat_bean_from_purchaser_product";
    public static final String IM_SUPPLIER_SELLER_ID = "im_supplier_seller_id"; //供应商SellerId
    public static final String IM_SUPPLIER_SELLER_NAME = "im_purchaser_seller_name"; //供应商sellerName
    public static final String IM_PURCHASER_USER_ID = "im_purchaser_user_id"; //采购商uerId
    public static final String IM_SUPPLIER_USER_ID = "im_supplier_user_id"; //供应商uerId
    public static final String IM_PURCHASER_SELLER_ID = "im_purchaser_seller_id"; //采购商sellerId
    public static final String IM_GROUP_ID = "group_id"; //群号
    public static final String IM_GROUP_NAME = "group_name"; //群名
    public static final String MSG_DEST_USER_INFO = "msg_dest_user_info"; //联系人
    public static final String GROUP_ID = "group_id"; //群号
    public static final String CATE_SHOP_ID = "cate_shop_id"; //栏目编号

    public static String DATA = "data";//acitivity数据对象传递key
    public static String APP_BUILD_VERSION = "appBuildVersion"; //版本标识
    public static String APP_VERSION = "appVersion"; //版本ID
    public static String PASSORT_ID = "passport_id"; //通行证号
    public static String UUID_ID = "UUID"; //手机唯一标识码
    public static String LONG_ID = "longitude"; //纬度
    public static String LAT_ID = "latitude"; //经度

    public static String LOGIN_SHOP_ID = "login_shop_id"; //代理商店铺编号
    public static String AUTH_STATUS = "auth_status";//身份证件状态
    public static String USER_ID = "user_id"; //用户id
    public static String USER_NAME = "user_name";//姓名
    public static String USER_ACCOUNT = "account";//帐号(用户名/手机号/邮箱)
    public static String USER_PASSWORD = "password";//密码
    public static String USER_ALIAS_NAME = "user_alias_name";//用户别名
    public static String NICK_NAME = "nick_name";//昵称
    public static String MEMBER_NAME = "member_name";//会员名
    public static String SEX = "sex";//性别
    public static String BIRTHDAY = "birthday";//生日
    public static String LOGO_URL = "logo_url";//用户肖像
    public static String LOGO = "logo";//用户肖像
    public static String MOBILE_NO = "mobile_no";//手机号
    public static String MOBILE = "mobile";//手机号
    public static String EMAIL_ADDRESS = "email_address";//邮箱
    public static String USER_ID_NUMBER = "user_ID_number";//身份证号
    public static String AUTH_INFO = "auth_info";//认证资料
    public static String VENDOR_SHOP_URL_INFO = "vendor_shop_url_info";//认证资料
    public static String GOODS_BRAND_ID = "goods_brand_id";//品牌编号
    public static String LABEL_SYS_ID = "label_sys_id";//品牌编号
    public static String CREATE_SELLER_ID = "create_seller_id";//代理商id
    public static String MIN_MSG_ID = "min_msg_id";//最小编号
    public static String MIX_STR = "mix_str";//条件：标题、单号、客户名称
    public static String MSG_TYPE = "msg_type";//条件：供给类型
    public static String MAX_USER_ID = "max_user_id";//最大编号
    public static String SUPPLY_ID = "supply_id";//供给编号
    public static String REQUEST_ID = "request_id";//需求id
    public static String GOODS_KEY_WORD = "goods_key_word";//关键词
    public static String GOODS_NAME = "goods_name";//商品别名
    public static String CATE_SYS_ALIAS_ID = "cate_sys_alias_id";//系统类别
    public static String SALES_CHANNELS_TYPE = "sale_channels_type";//销售渠道
    public static String TEL_NO = "tel_no";//公司号码
    public static String TEAM_TYPE_ID = "team_type_id";//公司规模 1:1-10人企业 2：11-50人企业　３：51-300人企业　４：300人以上企业
    public static String GOODS_DETAIL_DESC = "goods_detail_desc";//描述
    public static String GOODS_NO = "goods_no";//货号
    public static String QTY_PER_CASE = "qty_per_case";//箱量
    public static String GOODS_BARCODE = "goods_barcode";//自定义条码
    public static String UNIT_SKU = "unit_sku";//计量单位
    public static String UNIT_SALE = "unit_sale";//销售方式
    public static String UNIT_SIXZE = "unit_sixze";//打包单位
    public static String ATTR_LIST = "attr_list"; //属性搜索
    public static String ATTR_SYS_LIST = "attr_sys_list"; //商品属性
    public static String ATTR_CUSTOM_LIST = "attr_custom_list";//自定义商品属性
    public static String GOODS_PIC_LIST = "goods_pic_list";//商品图片
    public static String GOODS_STD_PRICE_LIST = "goods_std_price_list";//商品规格
    public static String STD_CATE_CUSTOM_INFO = "std_cate_custom_info";//自定义商品规格
    public static String GOODS_ID = "goods_id";//商品id
    public static String IS_HOT = "is_hot";//是否热销
    public static String LOGIN_USER_ID = "login_user_id";//登陆者用户id
    public static String LOGIN_SELLER_ID = "login_seller_id";//登陆者公司id
    public static String STATUS = "status";//上架状态
    public static String MODIFY_TYPE = "modify_type";//操作类型 1修改价格 2修改货号
    public static String MODIFY_INFO = "modify_info";//修改信息
    public static String MODIFY_TAG = "modify_tag";//修改信息
    public static String PAGESIZE = "pagesize";//页码大小
    public static String PAGEINDEX = "pageindex";//当前页码
    public static String SUPPLY_INFO = "supply_info";//添加供给信息数组
    public static String SUPPLY_PIC_INFO = "supply_pic_info";//添加供给图片数组
    public static String SUPPLY_GOODS_INFO = "supply_goods_info";//添加供给产品数组
    public static String MOQ = "moq";//起订量
    public static String UNIT_ID = "unit_id";//单位编号
    public static String UNIT_NAME = "unit_name";//单位名
    public static String SOURE_ADDR = "soure_addr";//货源地
    public static String MIN_SUPPLY_ID = "min_supply_id";// 	上次最小供给编号
    public static String MIN_REQUEST_ID = "min_request_id";//上次最小需求编号
    public static String AUTH_COMPANY_NEED = "company_need_certify";
    public static String AGENT_SELLER_ID = "agent_seller_id";//代理商编号
    public static String GOODS_SUPPLY_TYPE = "goods_supply_type";//通货类型
    public static String PRICE_FLOOR = "price_floor";//价格区间，最低价格
    public static String PRICE_CEIL = "price_ceil";//价格区间，最高价格
    public static String GOODS_STD_LIST = "goods_std_list";//发布规格信息
    public static String SUPPLY_TYPE = "supply_type";//供货类型
    public static String VALIDITY_DAY = "validity_day";//有效期
    public static String RECEIPT_TYPE = "receipt_type";//收款方式;
    public static String DESCRPTION = "descrption";//供给描述
    public static String CUSTOMER_ID = "customer_id";//供应商编号id
    public static String CUSTOMER_USER_ID = "customer_user_id";//供应商编号id
    public static String TOKEN = "token";//Token
    public static String TYPE = "type";//Token


    public static String COMPANY_LOGO_URL = "company_logo"; //公司URL
    public static String COMPANY_INFO_DATA = "company_data"; //公司创建相关信息
    public static String COMPANY_NAME = "company_name";
    public static String PLATFORM_SELLER_ID = "create_seller_id"; //创建用户ID
    public static String SELLER_NAME = "seller_name"; //公司名称
    public static String SMALL_LOGO_URL = "small_logo_url"; //APP端logo
    public static String LEGAL_PERSON = "legal_person"; //法人
    public static String SELLER_TYPE = "seller_type"; //公司类型

    //~~~~~~~~~~~~~~~~~~~~~~ 获取公司类型 ~~~~~~~~~~~~~~~~~~~~
    public static String SELLER_ID = "seller_id"; //代理商编号
    public static String SHOP_ID = "shop_id"; //代理商店铺编号
    public static String THEME_ID = "theme_id"; //模板编号

    public static String INQUIRY_ID = "inquiry_id";//心愿单编号
    public static String CATE_2_LEVEL = "cate_2_level";//第二级类目
    public static String CATE_CHILD_LEVEL = "cate_child_level";//第二级类目
    public static String ORDER_WITH_PRICE = "order_with_price";//第二级类目


    public static String IS_DEFAULT_ADDRESS = "is_default_address";//用于筛选默认地址,1时取默认地址  0全部
    public static String DELIVER_ADDR_ID = "deliver_addr_id";//配送id
    public static String DELIVER_NAME = "deliver_name";//地址
    public static String CONTACT_TEL = "contact_tel";//联系电话
    public static String CONTACT_MOBILE = "contact_mobile";//手机号码
    public static String COUNTRY_ID = "country_id";//国家编号
    public static String ADDRESS_ID = "address_id";//地区编号
    public static String ADDRESS_PART1 = "address_part1";//地区全称
    public static String ADDRESS_PART2 = "address_part2";//乡村街道

    public static String POSITION = "position";

    //供给公司seller_id supply_id
    public static String COMP_GOODS_ID = "goods_id";
    public static String COMP_SELLER_ID = "seller_id";
    public static String COMP_SUPPLY_ID = "supply_id";
    public static String COMMODITY_DETAILS_INFO = "commodity_info";
    public static String MIN_GOODS_ID = "min_goods_id";//最小产品编号
    public static String GOODS_INFO = "goods_info";//报价产品数组
    public static String ID = "id";//删除编号
    public static String GOODS_STD_INFO = "goods_std_info";//规格信息
    public static String QUOTE_ID = "quote_id";//报价单号
    public static String LOOK_TYPE = "look_type";//【1:采购商 2:供应商】
    public static String IS_INQUIRYED = "is_inquiryed";//
    public static String CART_UUID = "cart_uuid";//进货单编号
    public static String CUSTOMER_TYPE = "customer_type";//订单类型
    public static String CART_INFO = "cart_info";//购物车数组

    public static String ORDER_ID = "order_id";//订单id
    public static String CHECK_STATUS = "check_status";//订单状态

    public static String INQUIRY_TYPE = "inquiry_type"; //类型：1:我要询价，2：我要定制  我要询价
    public static String MSG_ID = "msg_id";//消息id
    public static String URL = "url";//地址
    public static String FILTER = "filter";
    public static String IS_INQUIRY = "is_inquiry";//询价商品列表1
    public static String MAX_STR = "max_str";//客户名称条件
    public static String MAX_NO = "max_no";//订单编号条件
    public static String MSG_TITLE = "msg_title";//客户名称条件

    public static String TAG = "tag";//标记
    public static String OSTATUS = "ostatus";//订单状态
    public static String IS_SELLER = "is_seller";//是否为销售
    public static String SELF_USER_ID = "self_user_id";//销售代表id
    public static String CHECKED_STATUS = "checked_status";
    public static String SERIAL_NO = "serial_no";
    public static String REMARKS = "remarks";//备注名
    public static String RELATIONSHIP_EMPLOYEE_ID = "relationship_employee_id";//客户关联id//供应
    public static String SEARCH_CONDITION = "search_condition";//筛选条件
    public static String ROLE = "role";//角色
    public static String OWNER_SUPPLIER_ID = "owner_supplier_id";//客户关联id//采购
    public static String IS_MANAGER = "is_manager";//是否管理员
    public static String HOT_TYPE = "hot_type";
    public static String MSG_COUNT = "msg_count";

    public static String AD_SIZE_NAME = "ad_size_name"; //1280_629_1//采购  、、 1280_629_2;//供
    public static String ORDERTYPE="ordertype";  //2倒叙

    public static String RECOMMEND_ID="recommend_id";
    public static String SUBSCRIPTION_INFO="subscription_info"; //订阅消息
}
