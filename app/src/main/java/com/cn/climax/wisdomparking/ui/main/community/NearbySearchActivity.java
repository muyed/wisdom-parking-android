package com.cn.climax.wisdomparking.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseActivity;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.base.activity.CustomSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.main.home.city.CityPickerActivity;
import com.cn.climax.wisdomparking.widget.citypicker.model.LocateState;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class NearbySearchActivity extends BaseSwipeBackActivity {

    @BindView(R.id.atvLeftTitle)
    TextView atvLeftTitle;
    @BindView(R.id.atvToolBarMainSearch)
    EditText atvToolBarMainSearch;

    private AMapLocationClient mLocationClient;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;
    private PoiSearch poiSearch;

    @Override
    protected int initContentView() {
        return R.layout.activity_nearby_search;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initLocation();
        initSearch();
    }

    private void initSearch() {
        atvToolBarMainSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = atvToolBarMainSearch.getText().toString().trim();
                //判断内容不为空
                if (null != content && !content.isEmpty()) {
                    //通过Query设置搜索条件,第一个参数为搜索内容,第二个参数为搜索类型，第三个参数为搜索范围（空字符串代表全国）。
                    PoiSearch.Query query = new PoiSearch.Query(content, "", "");
                    poiSearch = new PoiSearch(NearbySearchActivity.this, query);
                    poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                        @Override
                        public void onPoiSearched(PoiResult poiResult, int errcode) {
                            //判断搜索成功
                            if (errcode == 1000) {
                                if (null != poiResult && poiResult.getPois().size() > 0) {
                                    for (int i = 0; i < poiResult.getPois().size(); i++) {
                                        Log.i("TAG_MAIN", "POI 的行政区划代码和名称=" + poiResult.getPois().get(i).getAdCode() + "," + poiResult.getPois().get(i).getAdName());
                                        Log.i("TAG_MAIN", "POI的所在商圈=" + poiResult.getPois().get(i).getBusinessArea());
                                        Log.i("TAG_MAIN", "POI的城市编码与名称=" + poiResult.getPois().get(i).getCityCode() + "," + poiResult.getPois().get(i).getCityName());
                                        Log.i("TAG_MAIN", "POI 的经纬度=" + poiResult.getPois().get(i).getLatLonPoint());
                                        Log.i("TAG_MAIN", "POI的地址=" + poiResult.getPois().get(i).getSnippet());
                                        Log.i("TAG_MAIN", "POI的名称=" + poiResult.getPois().get(i).getTitle());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onPoiItemSearched(PoiItem poiItem, int i) {

                        }
                    });
                    poiSearch.searchPOIAsyn();
                }
            }
        });
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(new WeakReference<>(NearbySearchActivity.this).get());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        String location = district.contains("县") ? district.substring(0, district.length() - 1) : city.substring(0, city.length() - 1);
                        updateLocateState(LocateState.SUCCESS, location);
                    } else {
                        //定位失败
                        updateLocateState(LocateState.FAILED, null);
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    /**
     * 更新定位状态
     */
    public void updateLocateState(int state, String city) {
        this.locateState = state;
        this.locatedCity = city;

        switch (locateState) {
            case LocateState.LOCATING:
                atvLeftTitle.setText(getString(R.string.cp_locating));
                break;
            case LocateState.FAILED:
                atvLeftTitle.setText(R.string.cp_located_failed);
                break;
            case LocateState.SUCCESS:
                atvLeftTitle.setText(locatedCity);
                break;
        }
    }

    @OnClick({R.id.allToLocation})
    void click(View view) {
        switch (view.getId()) {
            case R.id.allToLocation: //城市选择
                startActivity(new Intent(NearbySearchActivity.this, CityPickerActivity.class));
                break;
        }
    }

}
