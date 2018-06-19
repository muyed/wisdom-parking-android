package com.cn.climax.wisdomparking.ui.main.licenseplate;

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
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.base.help.FullyLinearLayoutManager;
import com.cn.climax.wisdomparking.data.response.CarLicenseMineBean;
import com.cn.climax.wisdomparking.widget.swipemenu.SwipeMenuDeleteRecyclerView;
import com.cn.climax.wisdomparking.widget.swipemenu.adapter.SwipeMenuLicenseDeleteAdapter;
import com.cn.climax.wisdomparking.widget.xrecyclerview.SpacesItemDecoration;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class LicenseManagerListActivity extends BaseSwipeBackActivity {

    @BindView(R.id.xrvLicenseListView)
    SwipeMenuDeleteRecyclerView xrvLicenseListView;
    @BindView(R.id.llSkip2AddCarLicense)
    LinearLayout llSkip2AddCarLicense;

    private SwipeMenuLicenseDeleteAdapter mAdapter;
    private boolean isFromSelect = false;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "车牌管理");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_license_manager_list;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        isFromSelect = getIntent().getBooleanExtra("is_from_select",false);
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvLicenseListView.setLayoutManager(layoutManager);
        xrvLicenseListView.addItemDecoration(new SpacesItemDecoration(0, ScreenUtil.dip2px(this, 0.5f), 0, 0));

        mAdapter = new SwipeMenuLicenseDeleteAdapter(this);
        xrvLicenseListView.setAdapter(mAdapter);

        llSkip2AddCarLicense.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                startActivity(new Intent(LicenseManagerListActivity.this, AddLicensePlateActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyLicenseList();
    }

    private void getMyLicenseList() {
        ApiManage.get(ApiHost.getInstance().getMyCarLicenseList())
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                List<CarLicenseMineBean> carLicenseMineBeen =  com.alibaba.fastjson.JSONObject.parseArray(String.valueOf(json.get("data")), CarLicenseMineBean.class);
                                mAdapter.setDatas(carLicenseMineBeen, isFromSelect);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }
}
