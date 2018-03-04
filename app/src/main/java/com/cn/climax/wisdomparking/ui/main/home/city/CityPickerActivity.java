package com.cn.climax.wisdomparking.ui.main.home.city;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseActivity;
import com.cn.climax.wisdomparking.widget.citypicker.adapter.CityListAdapter;
import com.cn.climax.wisdomparking.widget.citypicker.adapter.ResultListAdapter;
import com.cn.climax.wisdomparking.widget.citypicker.db.DBManager;
import com.cn.climax.wisdomparking.widget.citypicker.model.City;
import com.cn.climax.wisdomparking.widget.citypicker.model.LocateState;
import com.cn.climax.wisdomparking.widget.citypicker.view.SideLetterBar;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author：leo on 2017/3/16 15:20
 * email： leocheung4ever@gmail.com
 * description: 城市选择
 * what & why is modified:
 */

public class CityPickerActivity extends BaseActivity {

    public static final String KEY_PICKED_CITY = "picked_city";

    @BindView(R.id.listview_all_city)
    ListView mListView;
    @BindView(R.id.listview_search_result)
    ListView mResultListView;
    @BindView(R.id.tv_letter_overlay)
    TextView overlay;
    @BindView(R.id.side_letter_bar)
    SideLetterBar mLetterBar;
    @BindView(R.id.et_search)
    EditText searchBox;
    @BindView(R.id.iv_search_clear)
    ImageView clearBtn;
    @BindView(R.id.empty_view)
    ViewGroup emptyView;

    private DBManager dbManager;
    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;

    private AMapLocationClient mLocationClient;

    @Override
    protected int initContentView() {
        return R.layout.cp_activity_city_list;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initData();
        initView();
        initLocation();
    }

    private void initData() {
        dbManager = new DBManager(new WeakReference<>(CityPickerActivity.this).get());
        dbManager.copyDBFile();
        List<City> mAllCities = dbManager.getAllCities();
        mCityAdapter = new CityListAdapter(new WeakReference<>(CityPickerActivity.this), mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                back(name);
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                mLocationClient.startLocation();
            }
        });

        mResultAdapter = new ResultListAdapter(this, null);
    }

    private void initView() {
        mListView.setAdapter(mCityAdapter);

        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                back(mResultAdapter.getItem(position).getName());
            }
        });
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(new WeakReference<>(CityPickerActivity.this).get());
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
                        mCityAdapter.updateLocateState(LocateState.SUCCESS, location);
                    } else {
                        //定位失败
                        mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    private void back(String city) {
        Intent data = new Intent();
        data.putExtra(KEY_PICKED_CITY, city);
        setResult(RESULT_OK, data);
        finish();
    }

    @OnClick({R.id.back, R.id.iv_search_clear})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_search_clear:
                searchBox.setText("");
                clearBtn.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                mResultListView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
    }
}
