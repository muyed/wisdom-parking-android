package com.cn.climax.wisdomparking.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
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
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.cn.climax.i_carlib.logcat.ZLog;
import com.cn.climax.i_carlib.uiframework.banner.CustomPageTransformer;
import com.cn.climax.i_carlib.uiframework.banner.XBanner;
import com.cn.climax.i_carlib.uiframework.banner.holder.XBViewHolderCreator;
import com.cn.climax.i_carlib.uiframework.banner.listener.OnItemClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.fragment.BaseFragment;
import com.cn.climax.wisdomparking.ui.main.holder.LocalImageHolderView;
import com.cn.climax.wisdomparking.ui.main.home.MineTravelActivity;
import com.cn.climax.wisdomparking.ui.main.home.city.AddressSelectedActivity;
import com.cn.climax.wisdomparking.ui.main.home.city.CityPickerActivity;
import com.cn.climax.wisdomparking.widget.My2dMapView;
import com.cn.climax.wisdomparking.widget.NoUnderlineSpan;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author：leo on 2018/3/3 19:44
 * email： leocheung4ever@gmail.com
 * description: TODO
 * what & why is modified:
 */

public class MainHomeFragment extends BaseFragment implements OnItemClickListener,
        AMapLocationListener, LocationSource {

    private static final String TAG = MainHomeFragment.class.getSimpleName();

    //~~~~~~~~~~~~~~~~~~~~~~~~~  首页展示部分 ~~~~~~~~~~~~~~~~~~~~~~~~~~
    @BindView(R.id.locationMapView)
    My2dMapView locationMapView;
    @BindView(R.id.atvLeftTitle)
    TextView atvLeftTitle;
    @BindView(R.id.xbanner)
    XBanner xbanner;
    @BindView(R.id.tv_click_to_bind)
    TextView tv_click_to_bind;
    @BindView(R.id.allClickToAddressSetting)
    LinearLayout allClickToAddressSetting;

    private AMapLocationClient mLocationClient;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private boolean isFirstLoc = true;

    private ArrayList<Integer> localImages = new ArrayList<>();
    private String mLocationCity;

    @Override
    protected void getBundle(Bundle bundle) {
    }

    @Override
    protected void initUI(View view, @Nullable Bundle savedInstanceState) {
        locationMapView.onCreate(savedInstanceState); //必须写
        initializeMap();
        initBanner();
        initView();
    }

    private void initializeMap() {
        if (aMap == null)
            aMap = locationMapView.getMap();
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);

        UiSettings settings = aMap.getUiSettings(); //设置显示定位按钮 并且可以点击
        aMap.setLocationSource(this); //设置监听 这里实现LocationSource接口
        settings.setMyLocationButtonEnabled(false); //是否显示定位按钮
        settings.setZoomControlsEnabled(false); //是否显示缩放按钮 此处设置不显示
        settings.setCompassEnabled(false); //显示指南针
        aMap.setMyLocationEnabled(true); //显示定位层 并且可以出发定位 默认是false

        //获得当前定位信息
        mLocationClient = new AMapLocationClient(getContext());
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    @Override
    protected void initData() {
        showContent(false);
    }

    @Override
    protected int initContentView() {
        return R.layout.fragment_main_home;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        locationMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        xbanner.startTurning(3000);
        locationMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        xbanner.stopTurning();
        locationMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        xbanner.stopTurning();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        xbanner.stopTurning();
        locationMapView.onDestroy();
    }

    private void initBanner() {
        //todo for testing
        localImages.clear();
        for (int position = 0; position < 7; position++) {
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
        }

        xbanner.setPages(new XBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setOnItemClickListener(this)
                .getViewPager()
                .setPageTransformer(true, CustomPageTransformer.setPageTransFormer(getResources().getInteger(R.integer.transformer_default), xbanner));
    }

    private void initView() {
        SpannableStringBuilder toBindBuilder = new SpannableStringBuilder(tv_click_to_bind.getText().toString());
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_2895ca));
        BackgroundColorSpan whiteSpan = new BackgroundColorSpan(ContextCompat.getColor(getContext(), R.color.white));
        NoUnderlineSpan toClickBindSpan = new NoUnderlineSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(new WeakReference<>(getActivity()).get(), "去绑定", Toast.LENGTH_SHORT).show();
            }
        };
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(32);
        toBindBuilder.setSpan(blueSpan, 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toBindBuilder.setSpan(toClickBindSpan, 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toBindBuilder.setSpan(whiteSpan, 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toBindBuilder.setSpan(sizeSpan, 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_click_to_bind.setText(toBindBuilder);
        tv_click_to_bind.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ZLog.e("onActivityResult：回来了 " + requestCode + " code: " + resultCode);
        if (requestCode == Activity.RESULT_FIRST_USER && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                mLocationCity = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                atvLeftTitle.setText(mLocationCity);
            }
        }
    }

    @OnClick({R.id.allToLocation, R.id.allClickToAddressSetting,
            R.id.aflClickToMyDrive,
            R.id.aflClickToCarInspect, R.id.aflClickToDriveStatistics,
            R.id.aflClickToFindFriend, R.id.aflClickToNavLocation})
    void click(View view) {
        switch (view.getId()) {
            case R.id.allToLocation:
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class), Activity.RESULT_FIRST_USER);
                break;
            case R.id.allClickToAddressSetting:
                Intent intent = new Intent(getActivity(), AddressSelectedActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.pop_up_anim, R.anim.pop_no_anim);
                break;
            case R.id.aflClickToMyDrive: //我的行程
                Intent travelIntent = new Intent(getActivity(), MineTravelActivity.class);
                startActivity(travelIntent);
                getActivity().overridePendingTransition(R.anim.pop_up_anim, R.anim.pop_no_anim);
                break;
            case R.id.aflClickToCarInspect: //车辆检测

                break;
            case R.id.aflClickToDriveStatistics: //驾驶统计
//                Intent statisticsIntent = new Intent(getActivity(), DriveStatisticsActivity.class);
//                startActivity(statisticsIntent);
//                getActivity().overridePendingTransition(R.anim.pop_up_anim, R.anim.pop_no_anim);
                break;
            case R.id.aflClickToFindFriend: //发现车友
//                Intent friendIntent = new Intent(getActivity(), FindCarFriendActivity.class);
//                startActivity(friendIntent);
//                getActivity().overridePendingTransition(R.anim.pop_up_anim, R.anim.pop_no_anim);
                break;
            case R.id.aflClickToNavLocation: //目的地下发

                break;
        }
    }

    private Integer getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude(); //获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy(); //获取精度信息
                amapLocation.getCityCode(); //获得城市编码
                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //去除蓝色透明范围圈
                    MyLocationStyle myLocationStyle = new MyLocationStyle();
                    myLocationStyle.radiusFillColor(android.R.color.transparent);
                    myLocationStyle.strokeColor(android.R.color.transparent);
                    myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.function_start));
                    aMap.setMyLocationStyle(myLocationStyle);
//                    aMap.addMarker(getLocationMarkerOptions());

                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(
                            new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);
                    isFirstLoc = false;
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mLocationClient = null;
    }
}
