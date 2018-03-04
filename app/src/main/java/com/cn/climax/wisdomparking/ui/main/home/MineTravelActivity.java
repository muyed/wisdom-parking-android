package com.cn.climax.wisdomparking.ui.main.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

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
import com.cn.climax.i_carlib.uiframework.bootstrap.AwesomeTextView;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.widget.My2dMapView;
import com.cn.climax.wisdomparking.widget.timeline.DotTimeLineAdapter;
import com.cn.climax.wisdomparking.widget.timeline.Event;
import com.cn.climax.wisdomparking.widget.timeline.itemdecoration.DotItemDecoration;
import com.cn.climax.wisdomparking.widget.timeline.itemdecoration.SpanIndexListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MineTravelActivity extends BaseSwipeBackActivity implements AMapLocationListener, LocationSource {

    @BindView(R.id.atvToolBarMainTitle)
    AwesomeTextView atvToolBarMainTitle;
    @BindView(R.id.latestMapView)
    My2dMapView latestMapView;
    @BindView(R.id.historyMapView)
    My2dMapView historyMapView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    List<Event> mList = new ArrayList<>();
    DotTimeLineAdapter mAdapter;
    DotItemDecoration mItemDecoration;

    long[] times = {
            1497229200,
            1497240000,
            1497243600,
            1497247200,
            1497249000,
            1497252600
    };
    String[] events = new String[]{
            "自定义事件1",
            "自定义事件2",
            "自定义事件3",
            "自定义事件4",
            "自定义事件5",
            "自定义事件6"
    };

    private AMapLocationClient mLatestLocationClient;
    private AMapLocationClient mHistoryLocationClient;
    private AMap aLatestMap;
    private AMap aHistoryMap;
    private LocationSource.OnLocationChangedListener mLatestListener;
    private LocationSource.OnLocationChangedListener mHistoryListener;
    private boolean isFirstLatestLoc = true;
    private boolean isFirstHistoryLoc = true;

    private ArrayList<Integer> localImages = new ArrayList<>();
    private String mLocationCity;

    @Override
    protected int initContentView() {
        return R.layout.activity_mine_travel;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        atvToolBarMainTitle.setText("我的行程");

        latestMapView.onCreate(savedInstanceState); //必须写
        initializeLatestMap();
        historyMapView.onCreate(savedInstanceState); //必须写
        initializeHistoryMap();

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mItemDecoration = new DotItemDecoration
                .Builder(this)
                .setOrientation(DotItemDecoration.VERTICAL)//if you want a horizontal item decoration,remember to set horizontal orientation to your LayoutManager
                .setItemStyle(DotItemDecoration.STYLE_DRAW)
                .setTopDistance(20)//dp
                .setItemInterVal(10)//dp
                .setItemPaddingLeft(20)//default value equals to item interval value
                .setItemPaddingRight(20)//default value equals to item interval value
                .setDotColor(Color.WHITE)
                .setDotRadius(2)//dp
                .setDotPaddingTop(0)
                .setDotInItemOrientationCenter(false)//set true if you want the dot align center
                .setLineColor(ContextCompat.getColor(this, R.color.color_5DC8FF))
                .setLineWidth(1)//dp
                .setEndText("END")
                .setTextColor(Color.WHITE)
                .setTextSize(10)//sp
                .setDotPaddingText(2)//dp.The distance between the last dot and the end text
                .setBottomDistance(40)//you can add a distance to make bottom line longer
                .create();
        mItemDecoration.setSpanIndexListener(new SpanIndexListener() {
            @Override
            public void onSpanIndexChange(View view, int spanIndex) {
                Log.i("Info", "view:" + view + "  span:" + spanIndex);
                view.setBackgroundResource(spanIndex == 0 ? R.drawable.pop_left : R.drawable.pop_right);
            }
        });
        mRecyclerView.addItemDecoration(mItemDecoration);

        for (int i = 0; i < times.length; i++) {
            Event event = new Event();
            event.setTime(times[i]);
            event.setEvent(events[i]);
            mList.add(event);
        }

        mAdapter = new DotTimeLineAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initializeLatestMap() {
        if (aLatestMap == null) {
            aLatestMap = latestMapView.getMap();
        }
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aLatestMap.setMapType(AMap.MAP_TYPE_NORMAL);

        UiSettings settings = aLatestMap.getUiSettings(); //设置显示定位按钮 并且可以点击
        aLatestMap.setLocationSource(this); //设置监听 这里实现LocationSource接口
        settings.setMyLocationButtonEnabled(false); //是否显示定位按钮
        settings.setZoomControlsEnabled(false); //是否显示缩放按钮 此处设置不显示
        settings.setCompassEnabled(false); //显示指南针
        aLatestMap.setMyLocationEnabled(true); //显示定位层 并且可以出发定位 默认是false

        //获得当前定位信息
        mLatestLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLatestLocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mLatestLocationClient.setLocationOption(mLocationOption);
        mLatestLocationClient.startLocation();
    }

    private void initializeHistoryMap() {
        if (aHistoryMap == null) {
            aHistoryMap = historyMapView.getMap();
        }
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aHistoryMap.setMapType(AMap.MAP_TYPE_NORMAL);

        UiSettings settings = aHistoryMap.getUiSettings(); //设置显示定位按钮 并且可以点击
        aHistoryMap.setLocationSource(this); //设置监听 这里实现LocationSource接口
        settings.setMyLocationButtonEnabled(false); //是否显示定位按钮
        settings.setZoomControlsEnabled(false); //是否显示缩放按钮 此处设置不显示
        settings.setCompassEnabled(false); //显示指南针
        aHistoryMap.setMyLocationEnabled(true); //显示定位层 并且可以出发定位 默认是false

        //获得当前定位信息
        mHistoryLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mHistoryLocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mHistoryLocationClient.setLocationOption(mLocationOption);
        mHistoryLocationClient.startLocation();
    }

    @OnClick({R.id.allMineCurrentCar})
    void click(View view) {
        switch (view.getId()) {
            case R.id.allMineCurrentCar:
                Intent travelIntent = new Intent(this, CurrentTravelActivity.class);
                startActivity(travelIntent);
                overridePendingTransition(R.anim.pop_up_anim, R.anim.pop_no_anim);
                break;
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
                if (isFirstLatestLoc) {
                    //去除蓝色透明范围圈
                    MyLocationStyle myLocationStyle = new MyLocationStyle();
                    myLocationStyle.radiusFillColor(android.R.color.transparent);
                    myLocationStyle.strokeColor(android.R.color.transparent);
                    myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.function_start));
                    aLatestMap.setMyLocationStyle(myLocationStyle);
//                    aMap.addMarker(getLocationMarkerOptions());

                    //设置缩放级别
                    aLatestMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                    //将地图移动到定位点
                    aLatestMap.moveCamera(CameraUpdateFactory.changeLatLng(
                            new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mLatestListener.onLocationChanged(amapLocation);
                    isFirstLatestLoc = false;
                }
                if (isFirstHistoryLoc) {
                    //去除蓝色透明范围圈
                    MyLocationStyle myLocationStyle = new MyLocationStyle();
                    myLocationStyle.radiusFillColor(android.R.color.transparent);
                    myLocationStyle.strokeColor(android.R.color.transparent);
                    myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.function_start));
                    aHistoryMap.setMyLocationStyle(myLocationStyle);
//                    aMap.addMarker(getLocationMarkerOptions());

                    //设置缩放级别
                    aHistoryMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                    //将地图移动到定位点
                    aHistoryMap.moveCamera(CameraUpdateFactory.changeLatLng(
                            new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mLatestListener.onLocationChanged(amapLocation);
                    isFirstHistoryLoc = false;
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
        mLatestListener = onLocationChangedListener;
        mHistoryListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mLatestLocationClient = null;
        mHistoryLocationClient = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        latestMapView.onSaveInstanceState(outState);
        historyMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        latestMapView.onResume();
        historyMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        latestMapView.onPause();
        historyMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        latestMapView.onDestroy();
        historyMapView.onDestroy();
    }
}
