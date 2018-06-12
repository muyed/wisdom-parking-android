package com.cn.climax.wisdomparking.ui.main.carport;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.local.CarLockParkingBean;
import com.cn.climax.wisdomparking.data.response.ParkingSpaceMineBean;
import com.cn.climax.wisdomparking.ui.main.carport.adapter.CarportAdapter;
import com.cn.climax.wisdomparking.ui.main.device.AddDeviceActivity;
import com.cn.climax.wisdomparking.ui.main.device.ReleaseLockActivity;
import com.cn.climax.wisdomparking.ui.main.share.PublishShareParkingActivity;
import com.cn.climax.wisdomparking.widget.pageview.PageIndicatorView;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.park.com.zxing.bean.ZxingConfig;
import okhttp3.Call;
import okhttp3.Response;

public class CarportMineActivity extends BaseSwipeBackActivity {

    private int REQUEST_CODE_SCAN = 111;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    @BindView(R.id.llSkip2Share)
    LinearLayout llSkip2Share;
    @BindView(R.id.llReleaseLock)
    LinearLayout llReleaseLock;

    @BindView(R.id.tvGoCertParking)
    TextView tvGoCertParking;
    @BindView(R.id.llNoParkingSpace)
    LinearLayout llNoParkingSpace;
    @BindView(R.id.tvCarSpaceNo)
    TextView tvCarSpaceNo;
    @BindView(R.id.tvMonthProfit)
    TextView tvMonthProfit;
    @BindView(R.id.ivAddCarport)
    ImageView ivAddCarport;

    private ArrayList<CarLockParkingBean> lockList = new ArrayList<>();
    private List<ParkingSpaceMineBean> mParkingSpaceMineBeanList = new ArrayList<>();
    private ParkingSpaceMineBean mCurParkingSpaceBean = new ParkingSpaceMineBean();

    private ZxingConfig config = new ZxingConfig();
    private boolean isClickVoice = true; //扫描是否开启声音 默认开启
    private boolean isNoCarportTag; //有没有车位标识

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "我的车位");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_identify_community;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initClick();
        getMyCarport();
    }

    private void getMyCarport() {
        ApiManage.get(ApiHost.getInstance().getMyCarport())
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                mParkingSpaceMineBeanList = com.alibaba.fastjson.JSONObject.parseArray(String.valueOf(json.get("data")), ParkingSpaceMineBean.class);
                                initLockPager();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    private void initClick() {
        llSkip2Share.setOnClickListener(new CommonClick());
        llReleaseLock.setOnClickListener(new CommonClick());
        ivAddCarport.setOnClickListener(new CommonClick());
    }

    @SuppressLint("SetTextI18n")
    private void initLockPager() {
        for (int i = 0; i < mParkingSpaceMineBeanList.size(); i++) {
            CarLockParkingBean parkingBean = new CarLockParkingBean();
            parkingBean.setLockName(mParkingSpaceMineBeanList.get(i).getCarportNum());
            if (!TextUtils.isEmpty(mParkingSpaceMineBeanList.get(i).getAlias()))
                parkingBean.setLockRemark(mParkingSpaceMineBeanList.get(i).getAlias());
            else
                parkingBean.setLockRemark("无备注");
            lockList.add(parkingBean);
        }
        if (lockList == null || lockList.size() == 0) {
            viewPager.setVisibility(View.GONE);
            pageIndicatorView.setVisibility(View.INVISIBLE);
            llNoParkingSpace.setVisibility(View.VISIBLE);

            isNoCarportTag = true;
        } else {
            isNoCarportTag = false;
            viewPager.setVisibility(View.VISIBLE);
            pageIndicatorView.setVisibility(View.VISIBLE);
            llNoParkingSpace.setVisibility(View.GONE);

            mCurParkingSpaceBean = mParkingSpaceMineBeanList.get(0);
            tvCarSpaceNo.setText(mCurParkingSpaceBean.getCarportNum());
            tvMonthProfit.setText(mCurParkingSpaceBean.getDeposit() + "");

            CarportAdapter adapter = new CarportAdapter();
            adapter.setData(createPageList(), lockList);
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onPageSelected(int position) {
                    mCurParkingSpaceBean = mParkingSpaceMineBeanList.get(position);
                    tvCarSpaceNo.setText(mCurParkingSpaceBean.getCarportNum());
                    tvMonthProfit.setText(mCurParkingSpaceBean.getDeposit() + "");
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    private List<View> createPageList() {
        List<View> pageList = new ArrayList<>();
        for (int i = 0; i < lockList.size(); i++) {
            pageList.add(createPageView(R.layout.view_pager_lock_item_layout));
        }
        return pageList;
    }

    @NonNull
    private View createPageView(int layoutId) {
        View view = View.inflate(CarportMineActivity.this, layoutId, null);
        return view;
    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.ivAddCarport:
                    startActivityForResult(new Intent(CarportMineActivity.this, AddDeviceActivity.class).putExtra("is_from_add", true), 199);
                    break;
                case R.id.llSkip2Share: //共享车位锁
                    if (isNoCarportTag) { //TODO 可能会是通过扫描获得车位绑定码 也可能是设备上手输绑定码 
                        startActivityForResult(new Intent(CarportMineActivity.this, AddDeviceActivity.class).putExtra("is_from_add", true), 199);
                    } else {
                        startActivity(new Intent(CarportMineActivity.this, PublishShareParkingActivity.class)
                                .putExtra("is_publish_mine", mParkingSpaceMineBeanList != null && mParkingSpaceMineBeanList.size() > 0)
                                .putExtra("parking_mine_bean", mCurParkingSpaceBean));
                    }
                    break;
                case R.id.llReleaseLock: //解锁车位锁
                    if (isNoCarportTag) { //TODO 可能会是通过扫描获得车位绑定码 也可能是设备上手输绑定码 
                        startActivityForResult(new Intent(CarportMineActivity.this, AddDeviceActivity.class).putExtra("is_from_add", true), 199);
                    } else {
                        startActivity(new Intent(CarportMineActivity.this, ReleaseLockActivity.class));
                    }
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 199 && resultCode == RESULT_OK) {
            initClick();
            getMyCarport();
        }
    }
}
