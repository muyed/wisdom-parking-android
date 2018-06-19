package com.cn.climax.wisdomparking.ui.main.carport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.i_carlib.util.phone.ScreenUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.Constant;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.ParkingSpaceMineBean;
import com.cn.climax.wisdomparking.ui.main.licenseplate.adapter.ParkingSpaceMineAdapter;
import com.cn.climax.wisdomparking.widget.xrecyclerview.ProgressStyle;
import com.cn.climax.wisdomparking.widget.xrecyclerview.SpacesItemDecoration;
import com.cn.climax.wisdomparking.widget.xrecyclerview.XRecyclerView;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class ParkingSpaceMineActivity extends BaseSwipeBackActivity {

    @BindView(R.id.xrvParkingListView)
    XRecyclerView xrvParkingListView;
    @BindView(R.id.llSkip2AuthParkingSpace)
    LinearLayout llSkip2AuthParkingSpace;

    private ParkingSpaceMineAdapter mAdapter;
    private boolean isEnableSelect;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "我的车位");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_parking_space_mine;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        isEnableSelect = getIntent().getBooleanExtra("is_enable_select", false);
        llSkip2AuthParkingSpace.setOnClickListener(new CommonClick());
        initListView();
    }

    private void initListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvParkingListView.setLayoutManager(layoutManager);
        xrvParkingListView.addItemDecoration(new SpacesItemDecoration(0, ScreenUtil.dip2px(this, 0.5f), 0, 0));
        xrvParkingListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrvParkingListView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xrvParkingListView.setArrowImageView(R.drawable.iconfont_downgrey);

        xrvParkingListView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        xrvParkingListView.getDefaultFootView().setLoadingDoneHint("我是有底线的");
        xrvParkingListView.setLimitNumberToCallLoadMore(2);
        xrvParkingListView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                listParkings(Constant.REFRESH);
            }

            @Override
            public void onLoadMore() {
                xrvParkingListView.setLoadingMoreEnabled(false);
            }
        });
        xrvParkingListView.refresh();

        mAdapter = new ParkingSpaceMineAdapter(this);
        xrvParkingListView.setAdapter(mAdapter);
    }

    private void listParkings(final int tag) {
        ApiManage.get(ApiHost.getInstance().getMyCarport())
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                List<ParkingSpaceMineBean> parkingSpaceMineBeanList = com.alibaba.fastjson.JSONObject.parseArray(String.valueOf(json.get("data")), ParkingSpaceMineBean.class);
                                if (tag == Constant.REFRESH) {
                                    mAdapter.setDatas(parkingSpaceMineBeanList, isEnableSelect);
                                    xrvParkingListView.refreshComplete();
                                } else {
                                    xrvParkingListView.setLoadingMoreEnabled(false);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });

    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.llSkip2AuthParkingSpace:
                    authParkingSpace();
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 199 && resultCode == RESULT_OK) {
            xrvParkingListView.refresh();
        }
    }

    private void authParkingSpace() {
        startActivityForResult(new Intent(ParkingSpaceMineActivity.this, AddDeviceActivity.class), 199);
    }
}
