package com.cn.climax.wisdomparking.ui.main.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeCustomActivity;
import com.cn.climax.wisdomparking.data.local.PoiAddressBean;
import com.cn.climax.wisdomparking.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.main.community.adapter.SearchAddressResultsAdapter;
import com.cn.climax.wisdomparking.ui.main.home.city.CityPickerActivity;
import com.cn.climax.wisdomparking.widget.citypicker.model.LocateState;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NearbySearchActivity extends BaseSwipeCustomActivity {

    @BindView(R.id.llNavBackToPre)
    LinearLayout llNavBackToPre;
    @BindView(R.id.atvLeftTitle)
    TextView atvLeftTitle;
    @BindView(R.id.atvToolBarMainSearch)
    EditText atvToolBarMainSearch;
    @BindView(R.id.rvSearchResultList)
    RecyclerView rvSearchResultList;

    private AMapLocationClient mLocationClient;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;
    private PoiSearch poiSearch;
    private SearchAddressResultsAdapter adapter;
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiResult mPoiResult;
    private String keyWord = "";// 要输入的poi搜索关键字

    @Override
    protected int initContentView() {
        return R.layout.activity_nearby_search;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initListView();
        initLocation();
        initSearch();
    }

    private void initListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSearchResultList.setLayoutManager(layoutManager);
        adapter = new SearchAddressResultsAdapter(this);
        rvSearchResultList.setAdapter(adapter);
    }

    private void initSearch() {
        atvToolBarMainSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                keyWord = String.valueOf(charSequence);
                currentPage = 0;
                if ("".equals(keyWord)) {
//                    ToastUtils.show("请输入搜索关键字");
                    return;
                } else {
                    doSearchQuery(keyWord);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = atvToolBarMainSearch.getText().toString().trim();
                if (s.length() > 0) {
                    atvToolBarMainSearch.setSelection(s.length());
                    rvSearchResultList.setVisibility(View.VISIBLE);
                } else {
                    rvSearchResultList.setVisibility(View.GONE);
                }
            }
        });
    }

    private void doSearchQuery(final String content) {
        //判断内容不为空
        if (null != content && !content.isEmpty()) {
            //通过Query设置搜索条件,第一个参数为搜索内容,第二个参数为搜索类型，第三个参数为搜索范围（空字符串代表全国）。
            final PoiSearch.Query query = new PoiSearch.Query(content, "", locatedCity);
            //这里没有做分页加载了,默认给50条数据
            query.setPageSize(30);// 设置每页最多返回多少条poi item
            query.setPageNum(currentPage);// 设置查第一页
            poiSearch = new PoiSearch(NearbySearchActivity.this, query);
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult poiResult, int errcode) {
                    //判断搜索成功
                    if (errcode == AMapException.CODE_AMAP_SUCCESS) {
                        if (poiResult.getQuery().equals(query)) {
                            mPoiResult = poiResult;
                            if (null != mPoiResult && mPoiResult.getPois().size() > 0) {
                                ArrayList<PoiAddressBean> data = new ArrayList<>();//自己创建的数据集合
                                // 取得搜索到的poiItems有多少页
                                List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiItem数据，页数从数字0开始
                                List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();// 当搜索不到poiItem数据时，会返回含有搜索关键字的城市信息
                                for (PoiItem item : poiItems) {
                                    //获取经纬度对象
                                    LatLonPoint llp = item.getLatLonPoint();
                                    double lon = llp.getLongitude();
                                    double lat = llp.getLatitude();

                                    String title = item.getTitle();
                                    String text = item.getSnippet();
                                    String provinceName = item.getProvinceName();
                                    String cityName = item.getCityName();
                                    String adName = item.getAdName();
                                    data.add(new PoiAddressBean(String.valueOf(lon), String.valueOf(lat), title, text, provinceName, cityName, adName));
                                }

                                adapter.setDatas(content, data);
                                for (int i = 0; i < mPoiResult.getPois().size(); i++) {
                                    Log.i("TAG_MAIN", "POI 的行政区划代码和名称=" + poiResult.getPois().get(i).getAdCode() + "," + poiResult.getPois().get(i).getAdName());
                                    Log.i("TAG_MAIN", "POI的所在商圈=" + poiResult.getPois().get(i).getBusinessArea());
                                    Log.i("TAG_MAIN", "POI的城市编码与名称=" + poiResult.getPois().get(i).getCityCode() + "," + poiResult.getPois().get(i).getCityName());
                                    Log.i("TAG_MAIN", "POI 的经纬度=" + poiResult.getPois().get(i).getLatLonPoint());
                                    Log.i("TAG_MAIN", "POI的地址=" + poiResult.getPois().get(i).getSnippet());
                                    Log.i("TAG_MAIN", "POI的名称=" + poiResult.getPois().get(i).getTitle());
                                }
                            } else {
                                adapter.setDatas("", null);
                            }
                        } else {
                            ToastUtils.show("附近暂无搜索结果");
                        }
                    } else {
                        ToastUtils.showerror(errcode);
                    }
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
            poiSearch.searchPOIAsyn();
        }
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

    @OnClick({R.id.llNavBackToPre, R.id.allToLocation})
    void click(View view) {
        switch (view.getId()) {
            case R.id.llNavBackToPre:
                finish();
                break;
            case R.id.allToLocation: //城市选择
                startActivityForResult(new Intent(NearbySearchActivity.this, CityPickerActivity.class), 99);
                break;
        }
    }

    /**
     * 设置详情地址
     */
    public void setDetailAddress(String detailAddress) {
        atvToolBarMainSearch.setText(detailAddress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            if (data != null) {
                locatedCity = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                atvLeftTitle.setText(locatedCity);
            }
        }
    }
}
