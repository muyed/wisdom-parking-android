package com.cn.climax.wisdomparking.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.response.CarLicenseMineBean;
import com.cn.climax.wisdomparking.data.response.LoginResponse;
import com.cn.climax.wisdomparking.data.response.NearbyParkingMineBean;
import com.cn.climax.wisdomparking.data.response.PublishShareOrder;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.wisdomparking.ui.account.LoginActivity;
import com.cn.climax.wisdomparking.ui.main.carport.ParkingSpaceImmMatchingActivity;
import com.cn.climax.wisdomparking.ui.main.community.AddCommunityActivity;
import com.cn.climax.wisdomparking.ui.main.community.AuthCommunityListActivity;
import com.cn.climax.wisdomparking.ui.main.community.CommunityIdentifyActivity;
import com.cn.climax.wisdomparking.ui.main.carport.ParkingSpaceMineActivity;
import com.cn.climax.wisdomparking.ui.main.device.LicenseManagerListActivity;
import com.cn.climax.wisdomparking.ui.main.device.ReleaseLockActivity;
import com.cn.climax.wisdomparking.ui.main.nav.Navigation2DActivity;
import com.cn.climax.wisdomparking.ui.main.order.OrderMineActivity;
import com.cn.climax.wisdomparking.ui.main.share.PublishShareParkingActivity;
import com.cn.climax.wisdomparking.ui.setting.AuthenticateCertActivity;
import com.cn.climax.wisdomparking.ui.setting.bank.BankCardListActivity;
import com.cn.climax.wisdomparking.ui.setting.CommonProblemsActivity;
import com.cn.climax.wisdomparking.ui.setting.CustomerServiceMineActivity;
import com.cn.climax.wisdomparking.ui.setting.DepositMineActivity;
import com.cn.climax.wisdomparking.ui.setting.MoreOptionsActivity;
import com.cn.climax.wisdomparking.ui.setting.NotifyMineActivity;
import com.cn.climax.wisdomparking.ui.setting.bank.WithDrawalBalanceActivity;
import com.cn.climax.wisdomparking.ui.setting.utils.AuthenticatedInfoActivity;
import com.cn.climax.wisdomparking.util.FlashUtils;
import com.cn.climax.wisdomparking.util.GlobalVerificateUtils;
import com.cn.climax.wisdomparking.util.HelperFromPermission;
import com.cn.climax.wisdomparking.widget.CircleView;
import com.cn.climax.wisdomparking.widget.My2dMapView;
import com.cn.climax.wisdomparking.widget.bottomdialog.BottomDialog;
import com.cn.climax.wisdomparking.widget.bulletinboard.adapter.SimpleBulletinAdapter;
import com.cn.climax.wisdomparking.widget.bulletinboard.view.BulletinView;
import com.cn.climax.wisdomparking.widget.numberkeyboard.OfoKeyboard;
import com.cn.climax.wisdomparking.widget.ofo.OfoConvcaveMenuActivity;
import com.lzy.okgo.callback.StringCallback;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.park.com.zxing.android.CaptureActivity;
import cn.park.com.zxing.bean.ZxingConfig;
import cn.park.com.zxing.common.Constant;
import okhttp3.Call;
import okhttp3.Response;

public class PeterMainActivity extends OfoConvcaveMenuActivity implements AMapLocationListener, LocationSource, PoiSearch.OnPoiSearchListener, AMap.OnMarkerClickListener, RouteSearch.OnRouteSearchListener {

    private int REQUEST_CODE_SCAN = 111;

    @BindView(R.id.atvToolBarMainTitle)
    TextView atvToolBarMainTitle;
    @BindView(R.id.mmvNavPark)
    My2dMapView mmvNavPark;

    @BindView(R.id.llOrderScan)
    LinearLayout llOrderScan;
    @BindView(R.id.ivSkip2Notify)
    ImageView ivSkip2Notify;
    @BindView(R.id.ivSkip2CustomerService)
    CardView ivSkip2CustomerService;
    @BindView(R.id.ivSkip2MyLocation)
    CardView ivSkip2MyLocation;

    @BindView(R.id.cvCenterAvatar)
    CircleView cvCenterAvatar;
    @BindView(R.id.tvSkipLoginReg)
    TextView tvSkipLoginReg; //登录/注册
    @BindView(R.id.llSkip2MoneyBag)
    LinearLayout llSkip2MoneyBag; //我的钱包
    @BindView(R.id.llSkip2Deposit)
    LinearLayout llSkip2Deposit; //押金
    @BindView(R.id.tvAccountBalance)
    TextView tvAccountBalance; //账户余额
    @BindView(R.id.llSkip2MineParkingSpace)
    LinearLayout llSkip2MineParkingSpace; //我的车位
    @BindView(R.id.llSkip2MineCar)
    LinearLayout llSkip2MineCar; //我的车辆
    @BindView(R.id.llSkip2MineOrder)
    LinearLayout llSkip2MineOrder; //我的订单
    @BindView(R.id.llSkip2MineDevice)
    LinearLayout llSkip2MineDevice; //我的设备
    @BindView(R.id.llSkip2MineCommunity)
    LinearLayout llSkip2MineCommunity; //我的小区
    @BindView(R.id.llSkip2MineService)
    LinearLayout llSkip2MineService; //我的客服
    @BindView(R.id.llSkip2CommonProblems)
    LinearLayout llSkip2CommonProblems; //常见问题
    @BindView(R.id.llSkip2MoreOption)
    LinearLayout llSkip2MoreOption; //更多
    @BindView(R.id.llSkip2IdentityInfo)
    LinearLayout llSkip2IdentityInfo; //身份信息认证
    @BindView(R.id.llSkip2MyBankCard)
    LinearLayout llSkip2MyBankCard; //我的银行卡
    @BindView(R.id.llSkip2IdentityCommunity)
    LinearLayout llSkip2IdentityCommunity; //小区认证

    @BindView(R.id.tvUserIdentityStatus)
    TextView tvUserIdentityStatus; //用户信息认证状态
    @BindView(R.id.tvCommunityIdentityStatus)
    TextView tvCommunityIdentityStatus; //小区认证状态

    @BindView(R.id.tvAccount)
    TextView tvAccount;

    @BindView(R.id.et_numberplate)
    EditText etNumberplate;
    @BindView(R.id.bt_sure)
    Button mBtnSure;
    @BindView(R.id.llOpenLight)
    LinearLayout llOpenLight; //手电筒
    @BindView(R.id.aivScanLight)
    AppCompatImageView aivScanLight; //手电筒
    @BindView(R.id.llOpenVoice)
    LinearLayout llOpenVoice; //声音
    @BindView(R.id.aivScanVoice)
    AppCompatImageView aivScanVoice; //声音
    @BindView(R.id.llOpenScanCode)
    LinearLayout llOpenScanCode; //扫码解锁

    private long exitTime = 0;
    private AMapLocationClient mNavLocationClient;
    private AMap aNavMap;
    private LocationSource.OnLocationChangedListener mNavListener;
    private boolean isFirstNavLoc = true;

    //    private static final String SEARCH_KEYWORD = "停车场";
    private static final String SEARCH_KEYWORD = "";
    private static final String POI_SEARCH_TYPE = "";

    private PoiSearch.Query query;
    private int currentPage = 0;
    private PoiSearch poiSearch;
    private PoiResult poiResult;

    private Marker lastCheckedMarker;
    private ArrayList<BitmapDescriptor> lastCheckedBitmapDescriptorList;
    private LatLonPoint endLocation;
    private String endAddress;
    private RouteSearch.DriveRouteQuery driveQuery;
    private ArrayList<PoiItem> poiItems;
    private LatLngBounds.Builder boundBuilder;
    private ArrayList<BitmapDescriptor> bitmapDescriptorArrayList;
    private LatLng mLatLng;

    private LoginResponse mUserInfoBean;
    boolean isGoToLogin;

    private List<Marker> mMarkerList = new ArrayList<>();
    private List<NearbyParkingMineBean> mNearbyParkingMineBeen = new ArrayList<>();
    private AMapLocation mAmapLocation;
    private OfoKeyboard mKeyboardView;

    private ZxingConfig config = new ZxingConfig();
    private boolean isClickVoice = true; //扫描是否开启声音 默认开启
    private BulletinView mBulletinView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        atvToolBarMainTitle.setText("彼得潘实业");
        mUserInfoBean = (LoginResponse) getIntent().getSerializableExtra("user_info_bean");
        isGoToLogin = SharedUtil.getInstance(this).get("is_login_success", false);
        if (!isGoToLogin) {
            startActivity(new Intent(PeterMainActivity.this, LoginActivity.class));
            finish();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //检查是否拥有定位权限
                if (!HelperFromPermission.checkPermission(PeterMainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    String[] perms = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
                    /***
                     * 没有授权，尝试获取权限。
                     * 1、第一次询问用户是否需要权限，弹出授权框。
                     * 2、以前被拒绝过，系统将不理会此程序的授权申请，弹出提示由用户自行处理。
                     */
                    if (ActivityCompat.shouldShowRequestPermissionRationale(PeterMainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        ActivityCompat.requestPermissions(PeterMainActivity.this, perms, 100);
                    } else {
                        HelperFromPermission.showPermissionDialog(PeterMainActivity.this, "定位权限");
                    }
                    return;
                }
            }
            mmvNavPark.onCreate(savedInstanceState); //必须写

            if (mUserInfoBean == null) {
                loginEvent();
            } else {
                initView();
            }
            if (!TextUtils.isEmpty(SharedUtil.getInstance(PeterMainActivity.this).get(ApiParamsKey.USER_NAME))) {
//                tvAccount.setText(SharedUtil.getInstance(PeterMainActivity.this).get(ApiParamsKey.USER_NAME));
                tvSkipLoginReg.setVisibility(View.GONE);
                tvAccount.setVisibility(View.VISIBLE);
                tvAccount.setText("退出登录");
            } else {
                tvSkipLoginReg.setVisibility(View.VISIBLE);
                tvAccount.setVisibility(View.GONE);
            }
            
            initBulletin();
        }
    }

    private void initBulletin() {
    }
    
    private void initView() {
        initMapView();
        initInfo();
        initInputKeyboard();
    }

    private void initInputKeyboard() {
        ivSkip2SearchList = (ImageView) findViewById(R.id.ivSkip2SearchList);
        mKeyboardView = new OfoKeyboard(PeterMainActivity.this);
        etNumberplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeyboardView.attachTo(etNumberplate, false, "");
                llSkip2Publish.setClickable(false);
                ivSkip2SearchList.setClickable(false);
            }
        });
        etNumberplate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mBtnSure.setTextColor(ContextCompat.getColor(PeterMainActivity.this, R.color.white));
                    mBtnSure.setBackgroundResource(R.drawable.activity_button_blue_border);
                    mKeyboardView.attachTo(etNumberplate, false, s.toString());
                } else {
                    mBtnSure.setTextColor(ContextCompat.getColor(PeterMainActivity.this, R.color.text_common_color));
                    mBtnSure.setBackgroundResource(R.drawable.activity_button_disable_grey_border);
                    mKeyboardView.attachTo(etNumberplate, false, "");
                }
            }
        });

        llOpenLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlashUtils.getInstance().switchFlash(aivScanLight);
            }
        });
        llOpenVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClickVoice) {
                    isClickVoice = false;
                    config.setPlayBeep(false);
                    aivScanVoice.setImageResource(R.drawable.icon_scan_voice_close);
                } else {
                    isClickVoice = true;
                    config.setPlayBeep(true);
                    aivScanVoice.setImageResource(R.drawable.icon_scan_voice);
                }
            }
        });
        llOpenScanCode.setOnClickListener(new CommClick());
        mKeyboardView.setOnOkClick(new OfoKeyboard.OnOkClick() {
            @Override
            public void onOkClick() {
                if (TextUtils.isEmpty(etNumberplate.getText().toString())) {
                    ToastUtils.show("请输入车牌号");
                    return;
                }
                ToastUtils.show("即将进行解锁功能");
                isOpenInputCenter = false;
                ofoMenuKeyBoardLayout.closeKeyboard();
//                releaseLock();
            }
        });
    }

    private void loginEvent() {
        HashMap<String, String> httpParams = new HashMap<>();
        httpParams.put(ApiParamsKey.USER_NAME, SharedUtil.getInstance(PeterMainActivity.this).get(ApiParamsKey.USER_NAME));
        httpParams.put(ApiParamsKey.PASSWORD, SharedUtil.getInstance(PeterMainActivity.this).get(ApiParamsKey.PASSWORD));
        httpParams.put(ApiParamsKey.TYPE, "1");
        JSONObject json = new JSONObject(httpParams);

        ApiManage.post(ApiHost.getInstance().login())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<LoginResponse>(PeterMainActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                        ToastUtils.show(msg);
                    }

                    @Override
                    protected void onExecuteSuccess(LoginResponse bean, Call call) {
                        SharedUtil.getInstance(PeterMainActivity.this).put("is_login_success", true);
                        SharedUtil.getInstance(PeterMainActivity.this).put(ApiParamsKey.REAL_NAME, bean.getRealName());
                        SharedUtil.getInstance(PeterMainActivity.this).put(ApiParamsKey.IS_AUTH, !TextUtils.isEmpty(bean.getRealName()));
                        SharedUtil.getInstance(PeterMainActivity.this).put(ApiParamsKey.IS_AUTH_COMMUNITY, bean != null && bean.getCommunityList() != null && bean.getCommunityList().size() > 0);
                        SharedUtil.getInstance(PeterMainActivity.this).put(ApiParamsKey.IS_AUTH_PARKING_SPACE, bean != null && bean.getUserCarportList() != null && bean.getUserCarportList().size() > 0);

                        judgeUserIsAddCarLicense();
                        mUserInfoBean = bean;
                        initView();
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {
                        ToastUtils.show(response.message());
                    }
                });
    }

    private void judgeUserIsAddCarLicense() {
        ApiManage.get(ApiHost.getInstance().getMyCarLicenseList())
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                List<CarLicenseMineBean> carLicenseMineBeen = com.alibaba.fastjson.JSONObject.parseArray(String.valueOf(json.get("data")), CarLicenseMineBean.class);

                                if (carLicenseMineBeen != null && carLicenseMineBeen.size() > 0) {
                                    SharedUtil.getInstance(PeterMainActivity.this).put(ApiParamsKey.IS_ADD_CAR_LICENSE, true);
                                } else {
                                    SharedUtil.getInstance(PeterMainActivity.this).put(ApiParamsKey.IS_ADD_CAR_LICENSE, false);
                                }
                            } catch (JSONException e) {
                                SharedUtil.getInstance(PeterMainActivity.this).put(ApiParamsKey.IS_ADD_CAR_LICENSE, false);
                                e.printStackTrace();
                            }
                        } else {
                            SharedUtil.getInstance(PeterMainActivity.this).put(ApiParamsKey.IS_ADD_CAR_LICENSE, false);
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void initInfo() {
        if (GlobalVerificateUtils.getInstance(this).isAuth()) {
            tvUserIdentityStatus.setText("已认证");
            tvUserIdentityStatus.setTextColor(ContextCompat.getColor(this, R.color.text_common_hint));
        } else {
            tvUserIdentityStatus.setText("请认证");
            tvUserIdentityStatus.setTextColor(ContextCompat.getColor(this, R.color.color_fd604f));
        }
        if (GlobalVerificateUtils.getInstance(this).isAuthCommunity()) {
            tvCommunityIdentityStatus.setVisibility(View.GONE);
            tvCommunityIdentityStatus.setText("已认证");
            tvCommunityIdentityStatus.setTextColor(ContextCompat.getColor(this, R.color.text_common_hint));
        } else {
            tvCommunityIdentityStatus.setText("请认证");
            tvCommunityIdentityStatus.setTextColor(ContextCompat.getColor(this, R.color.color_fd604f));
        }
        DecimalFormat df = new DecimalFormat("0.00");
        if (mUserInfoBean.getAccount() != null && mUserInfoBean.getAccount().getBalance() != 0)
            tvAccountBalance.setText("￥ " + df.format((float) mUserInfoBean.getAccount().getBalance() / 1));
        else
            tvAccountBalance.setText("￥ 0.00");

        ivSkip2CustomerService.setOnClickListener(new CommClick());
        ivSkip2MyLocation.setOnClickListener(new CommClick());
        ivSkip2Notify.setOnClickListener(new CommClick());
        llOrderScan.setOnClickListener(new CommClick());

        llSkip2Publish = ((LinearLayout) findViewById(R.id.llSkip2Publish));
        llSkip2Publish.setVisibility(View.VISIBLE);
        llSkip2Publish.setOnClickListener(new CommClick()); //发布共享单
        cvCenterAvatar.setOnClickListener(new CommClick()); //头像设置
        tvSkipLoginReg.setOnClickListener(new CommClick()); //登录/注册
        tvAccount.setOnClickListener(new CommClick()); //退出登录
        llSkip2MoneyBag.setOnClickListener(new CommClick()); //我的钱包
        llSkip2Deposit.setOnClickListener(new CommClick()); //押金
        llSkip2MineCar.setOnClickListener(new CommClick()); //我的车辆
        llSkip2MineParkingSpace.setOnClickListener(new CommClick()); //我的车位
        llSkip2MineOrder.setOnClickListener(new CommClick()); //我的订单
        llSkip2MineDevice.setOnClickListener(new CommClick()); //我的设备
        llSkip2MineCommunity.setOnClickListener(new CommClick()); //我的小区
        llSkip2MineService.setOnClickListener(new CommClick()); //我的客服
        llSkip2IdentityInfo.setOnClickListener(new CommClick()); //身份信息
        llSkip2MyBankCard.setOnClickListener(new CommClick()); //我的银行卡
        llSkip2IdentityCommunity.setOnClickListener(new CommClick()); //小区认证
        llSkip2CommonProblems.setOnClickListener(new CommClick()); //常见问题
        llSkip2MoreOption.setOnClickListener(new CommClick()); //更多

    }

    private void initMapView() {
        if (aNavMap == null) {
            aNavMap = mmvNavPark.getMap();
        }
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aNavMap.setMapType(AMap.MAP_TYPE_NORMAL);

        UiSettings settings = aNavMap.getUiSettings(); //设置显示定位按钮 并且可以点击
        aNavMap.setLocationSource(this); //设置监听 这里实现LocationSource接口
        settings.setMyLocationButtonEnabled(false); //是否显示定位按钮
        settings.setZoomControlsEnabled(false); //是否显示缩放按钮 此处设置不显示
        settings.setCompassEnabled(false); //显示指南针
        aNavMap.setMyLocationEnabled(true); //显示定位层 并且可以出发定位 默认是false
        aNavMap.setOnMarkerClickListener(this); //Marker点击事件

        //获得当前定位信息
        mNavLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mNavLocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mNavLocationClient.setLocationOption(mLocationOption);
        mNavLocationClient.startLocation();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isOpenPCenter) {
                if (ofoMenuLayout.isOpen()) {
                    ofoMenuLayout.close();
                    isOpenPCenter = false;
                }
            } else if (isOpenInputCenter) {
                if (ofoMenuKeyBoardLayout.isOpen()) {
                    ofoMenuKeyBoardLayout.close();
                    isOpenInputCenter = false;
                }
            } else {
                if ((System.currentTimeMillis() - exitTime) > 1000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    SharedUtil.getInstance(getApplicationContext()).put("last_launch", System.currentTimeMillis());
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLocationChanged(final AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude(); //获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy(); //获取精度信息
                amapLocation.getCityCode(); //获得城市编码
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstNavLoc) {
                    //去除蓝色透明范围圈
                    MyLocationStyle myLocationStyle = new MyLocationStyle();
                    myLocationStyle.radiusFillColor(android.R.color.transparent);
                    myLocationStyle.strokeColor(android.R.color.transparent);
                    myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_parking_loc));
                    aNavMap.setMyLocationStyle(myLocationStyle);
//                    aNavMap.addMarker(getLocationMarkerOptions());

                    //设置缩放级别
                    aNavMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                    //将地图移动到定位点
                    aNavMap.moveCamera(CameraUpdateFactory.changeLatLng(
                            new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mNavListener.onLocationChanged(amapLocation);
                    mLatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getNearbyByLatLng(amapLocation, mLatLng);
                        }
                    });

                    isFirstNavLoc = false;
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    private void getNearbyByLatLng(final AMapLocation amapLocation, LatLng latLng) {
        ApiManage.get(ApiHost.getInstance().loadByDistance() + latLng.longitude + "/" + latLng.latitude + "/" + "10")
                .tag(this)
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                mNearbyParkingMineBeen = com.alibaba.fastjson.JSONObject.parseArray(String.valueOf(json.get("data")), NearbyParkingMineBean.class);
                                if (mNearbyParkingMineBeen != null && mNearbyParkingMineBeen.size() > 0) {
                                    //根据指定经纬度 地图显示
                                    prepareSearchNearbyParking(amapLocation, mNearbyParkingMineBeen);
                                } else {
                                    Toast.makeText(PeterMainActivity.this, "附近暂无可用车位", Toast.LENGTH_LONG).show();
                                }

                                //开始POI搜索
//                                searchByPoi(amapLocation);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    private void prepareSearchNearbyParking(AMapLocation amapLocation, List<NearbyParkingMineBean> parkingMineBeanList) {
        this.mAmapLocation = amapLocation;
        boundBuilder = new LatLngBounds.Builder();
        String iconName = "icon_poi_marker_parking";
        int iconId = getResources().getIdentifier(iconName, "drawable", this.getPackageName());
        for (int j = 0; j < parkingMineBeanList.size(); j++) {
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(iconId));
            markerOptions.position(new LatLng(parkingMineBeanList.get(j).getLatitude(), parkingMineBeanList.get(j).getLongitude()));
            markerOptions.title(parkingMineBeanList.get(j).getCity()).snippet(parkingMineBeanList.get(j).getCity() + "：" + parkingMineBeanList.get(j).getLatitude() + "，" + parkingMineBeanList.get(j).getLongitude());
            markerOptions.draggable(true);//设置Marker可拖动
            Marker marker = aNavMap.addMarker(markerOptions);
            marker.setObject(j);
            //为了POI填充整个地图区域
            boundBuilder.include(new LatLng(parkingMineBeanList.get(j).getLatitude(), parkingMineBeanList.get(j).getLongitude()));
            mMarkerList.add(marker);
        }
        LatLngBounds bounds = boundBuilder.build();
        // 移动地图，所有marker自适应显示。LatLngBounds与地图边缘10像素的填充区域
        aNavMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }

    private void searchByPoi(AMapLocation amapLocation) {
        //设置搜索条件
        query = new PoiSearch.Query(SEARCH_KEYWORD, POI_SEARCH_TYPE, amapLocation.getCityCode());
        query.setPageSize(10); //设置每页最多返回poi item
        query.setPageNum(currentPage);
        //构造PoiSearch对象,设置监听
        poiSearch = new PoiSearch(PeterMainActivity.this, query);
        prepareSearchPeriphery(amapLocation); //检索周边POI
    }

    public void location() {
        aNavMap.moveCamera(CameraUpdateFactory.changeLatLng(mLatLng));
    }

    private void prepareSearchPeriphery(AMapLocation amapLocation) {
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude()), 3000));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(this);
        //调用异步搜索POI方法发送请求
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mNavListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mNavListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isGoToLogin) {
            mmvNavPark.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isGoToLogin) {
            mmvNavPark.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isGoToLogin) {
            mmvNavPark.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isGoToLogin) {
            mmvNavPark.onDestroy();
            poiResult = null;
            aNavMap.clear();
        }
        FlashUtils.getInstance().finishFlashUtils();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < mMarkerList.size(); i++) {
            if (marker.equals(mMarkerList.get(i))) {
                if (aNavMap != null) {
                    final int finalI = i;
                    new BottomDialog(PeterMainActivity.this)
                            .layout(BottomDialog.GRID)
                            .orientation(BottomDialog.VERTICAL)
                            .nav(new BottomDialog.OnSkip2NavigationListener() {
                                @Override
                                public void nav() {
                                    NaviLatLng startNavi = new NaviLatLng(mAmapLocation.getLatitude(), mAmapLocation.getLongitude());
                                    NaviLatLng endNavi = new NaviLatLng(mNearbyParkingMineBeen.get(finalI).getLatitude(), mNearbyParkingMineBeen.get(finalI).getLongitude());
                                    startActivity(new Intent(PeterMainActivity.this, Navigation2DActivity.class)
                                            .putExtra("start_navi_point", startNavi)
                                            .putExtra("end_navi_point", endNavi));
                                }
                            })
                            .match(new BottomDialog.OnSkip2MatchListener() {
                                @Override
                                public void match() {
                                    startActivity(new Intent(PeterMainActivity.this, ParkingSpaceImmMatchingActivity.class).putExtra("destination_navi_info", mNearbyParkingMineBeen.get(finalI)));
                                }
                            })
                            .setData(mAmapLocation, mNearbyParkingMineBeen.get(i))
                            .show();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {
                if (result.getQuery().equals(query)) { //是否是同一条
                    poiResult = result;
                    //取得搜索到的poiitem有多少页
                    int resultPages = poiResult.getPageCount();
                    //取得第一页的poiitem数据
                    poiItems = poiResult.getPois();
                    //当搜索不到poiitem数据时,会返回含有搜索关键字的城市信息
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();

                    if (poiItems != null && poiItems.size() > 0) {
                        boundBuilder = new LatLngBounds.Builder();
                        for (int j = 0; j < Math.min(poiItems.size(), 10); j++) {
                            String iconName = "icon_poi_marker_parking";
                            int iconId = getResources().getIdentifier(iconName, "drawable", this.getPackageName());
                            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(iconId));
                            markerOptions.position(new LatLng(poiItems.get(j).getLatLonPoint().getLatitude(), poiItems.get(j).getLatLonPoint().getLongitude()));
                            Marker marker = aNavMap.addMarker(markerOptions);
                            marker.setObject(j + 1);
                            //为了POI填充整个地图区域
                            boundBuilder.include(new LatLng(poiItems.get(j).getLatLonPoint().getLatitude(), poiItems.get(j).getLatLonPoint().getLongitude()));
                            if (j == 0) {
                                lastCheckedMarker = marker;
                                lastCheckedBitmapDescriptorList = lastCheckedMarker.getIcons();
                                ArrayList<BitmapDescriptor> bitmapDescriptorArrayList = new ArrayList<>();
                                bitmapDescriptorArrayList.add(BitmapDescriptorFactory.fromResource(R.drawable.icon_poi_marker_parking));
                                marker.setIcons(bitmapDescriptorArrayList);
                                marker.setObject(1);
//                                tvParkingLot.setText("1. " + poiItems.get(0).getTitle());
//                                tvDestLocation.setText(poiItems.get(0).getSnippet());
                                endLocation = poiResult.getPois().get(0).getLatLonPoint();
                                endAddress = poiResult.getPois().get(0).getTitle();
                            }

                            mMarkerList.add(marker);
                        }
                        LatLngBounds bounds = boundBuilder.build();
                        // 移动地图，所有marker自适应显示。LatLngBounds与地图边缘10像素的填充区域
                        aNavMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
                    } else if (suggestionCities != null && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        Toast.makeText(PeterMainActivity.this, "附近暂无搜索结果", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(PeterMainActivity.this, "附近暂无搜索结果", Toast.LENGTH_LONG).show();
            }
        } else {
            ToastUtils.show(rCode + "");
        }
    }

    /**
     * 含有搜索关键字的城市信息列表展示
     */
    private void showSuggestCity(List<SuggestionCity> suggestionCities) {
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
        //解析result获取路径规划结果
        if (rCode == 1000) {
            if (result != null && result.getDriveQuery() != null) {
                if (result.getDriveQuery().equals(driveQuery)) { //是否是同一条
                    DrivePath drivePath = result.getPaths().get(0);
                    DrivingRouteOverlay routeOverlay =
                            new DrivingRouteOverlay(this, aNavMap,//第一个参数是context，2.是地图
                                    drivePath, result.getStartPos(),//3.驾车线路，4.出发位置
                                    result.getTargetPos(), null); //5.终点位置
                    routeOverlay.removeFromMap(); //去掉DrivingRouteOverlay上所有的Marker
                    routeOverlay.addToMap(); //添加驾车线路到地图
                    routeOverlay.zoomToSpan(); //移动镜头当前视角
                }
            }
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    private class CommClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.llOpenScanCode: //去扫码
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
                        return;
                    judgePermission(true);
                    break;

                case R.id.llSkip2Publish: //发布共享单
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
                        return;
                    startActivity(new Intent(PeterMainActivity.this, PublishShareParkingActivity.class));
                    break;
                case R.id.ivSkip2CustomerService: //我的客服
                    startActivity(new Intent(PeterMainActivity.this, CustomerServiceMineActivity.class));
                    break;
                case R.id.ivSkip2MyLocation:
                    location();
                    break;
                case R.id.ivSkip2Notify: //我的通知
                    startActivity(new Intent(PeterMainActivity.this, NotifyMineActivity.class));
                    break;
                case R.id.llOrderScan: //扫描开锁
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
                        return;
                    judgePermission(false);
                    break;

                case R.id.cvCenterAvatar:
                    break;
                case R.id.tvAccount: //退出登录
                    startActivity(new Intent(PeterMainActivity.this, LoginActivity.class));
                    break;
                case R.id.tvSkipLoginReg: //登录/注册
                    startActivity(new Intent(PeterMainActivity.this, LoginActivity.class));
                    break;
                case R.id.llSkip2MoneyBag: //我的钱包
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
                        return;
//                    startActivity(new Intent(PeterMainActivity.this, WalletMineActivity.class).putExtra("user_account_info", mUserInfoBean.getAccount()));
                    startActivity(new Intent(PeterMainActivity.this, WithDrawalBalanceActivity.class));
                    break;
                case R.id.llSkip2Deposit: //押金
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
                        return;
                    startActivity(new Intent(PeterMainActivity.this, DepositMineActivity.class));
                    break;
                case R.id.llSkip2MineDevice: //我的设备
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
                        return;
                    startActivity(new Intent(PeterMainActivity.this, CommunityIdentifyActivity.class));
                    break;
                case R.id.llSkip2MineCommunity: //我的小区
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
                        return;
                    startActivity(new Intent(PeterMainActivity.this, AuthCommunityListActivity.class));
                    break;

                case R.id.llSkip2MineCar: //我的车辆
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
                        return;
                    startActivity(new Intent(PeterMainActivity.this, LicenseManagerListActivity.class).putExtra("user_carport_list", (Serializable) mUserInfoBean.getUserCarportList()));
                    break;
                case R.id.llSkip2MineOrder: //我的订单
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
                        return;
                    startActivity(new Intent(PeterMainActivity.this, OrderMineActivity.class));
                    break;
                case R.id.llSkip2MineService: //我的客服
                    startActivity(new Intent(PeterMainActivity.this, CustomerServiceMineActivity.class));
                    break;
                case R.id.llSkip2CommonProblems: //常见问题
                    startActivity(new Intent(PeterMainActivity.this, CommonProblemsActivity.class));
                    break;
                case R.id.llSkip2MoreOption: //更多
                    startActivity(new Intent(PeterMainActivity.this, MoreOptionsActivity.class));
                    break;

                case R.id.llSkip2IdentityInfo: //身份认证
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isAuth())
                        startActivity(new Intent(PeterMainActivity.this, AuthenticateCertActivity.class));
                    else
                        startActivity(new Intent(PeterMainActivity.this, AuthenticatedInfoActivity.class).putExtra("account_bean", mUserInfoBean));
                    break;
                case R.id.llSkip2IdentityCommunity: //考虑多小区情况 小区认证
//                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
//                        return;
//                    startActivity(new Intent(PeterMainActivity.this, AuthCommunityListActivity.class));
                    startActivity(new Intent(PeterMainActivity.this, AddCommunityActivity.class));
                    break;
                case R.id.llSkip2MyBankCard: //我的银行卡
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
                        return;
                    startActivity(new Intent(PeterMainActivity.this, BankCardListActivity.class));
                    break;
                case R.id.llSkip2MineParkingSpace: //我的车位
                    if (!GlobalVerificateUtils.getInstance(PeterMainActivity.this).isEnableOption(PeterMainActivity.this))
                        return;
                    startActivity(new Intent(PeterMainActivity.this, ParkingSpaceMineActivity.class));
                    break;
            }
        }
    }

    private void judgePermission(final boolean isCloseView) {
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Intent intent = new Intent(PeterMainActivity.this, CaptureActivity.class);
                                /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                                 * 也可以不传这个参数
                                 * 不传的话  默认都为默认不震动  其他都为true
                                 * */
                        config.setShake(true);
                        config.setShowFlashLight(true);
                        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                        startActivityForResult(intent, REQUEST_CODE_SCAN);

                        if (isCloseView)
                            ofoMenuKeyBoardLayout.closeKeyboard();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Uri packageURI = Uri.parse("package:" + getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(PeterMainActivity.this, "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
                    }
                }).start();
    }

    private void releaseLock() {
        HashMap<String, String> unLockMap = new HashMap<>();
        unLockMap.put(ApiParamsKey.ID, "5");
        JSONObject json = new JSONObject(unLockMap);

        ApiManage.post(ApiHost.getInstance().unLock())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<PublishShareOrder>(PeterMainActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                    }

                    @Override
                    protected void onExecuteSuccess(PublishShareOrder bean, Call call) {
                        if (bean.getCode() == 200) {
                            startActivity(new Intent(PeterMainActivity.this, ReleaseLockActivity.class));
                        }
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {
                    }

                    @Override
                    protected boolean setDialogShow() {
                        return false;
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                ToastUtils.show("扫描结果为：" + content);
            }
        }
        if (requestCode == REQUEST_CODE_SCAN && resultCode == 999) {
            if (data != null) {
                boolean isFromCapture = data.getBooleanExtra("is_from_capture", false);
                if (isFromCapture) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openKeyboard();
                            mKeyboardView.attachTo(etNumberplate, false, "");
                        }
                    }, 160);
                }
            }
        }
    }
}
