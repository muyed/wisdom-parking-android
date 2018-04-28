package com.cn.climax.wisdomparking.ui.main.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.util.phone.ScreenUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.base.help.RecyclerViewLayoutManager;
import com.cn.climax.wisdomparking.ui.main.device.adapter.LicenseManagerAdapter;
import com.cn.climax.wisdomparking.widget.xrecyclerview.SpacesItemDecoration;

import butterknife.BindView;

public class LicenseManagerListActivity extends BaseSwipeBackActivity {

    @BindView(R.id.xrvLicenseListView)
    RecyclerView xrvLicenseListView;
    @BindView(R.id.llSkip2AddCarLicense)
    LinearLayout llSkip2AddCarLicense;
    
    private LicenseManagerAdapter mAdapter;

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
        RecyclerViewLayoutManager layoutManager = new RecyclerViewLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvLicenseListView.setLayoutManager(layoutManager);
        xrvLicenseListView.addItemDecoration(new SpacesItemDecoration(0, ScreenUtil.dip2px(this, 0.5f), 0, 0));

        mAdapter = new LicenseManagerAdapter(this);
        xrvLicenseListView.setAdapter(mAdapter);

        llSkip2AddCarLicense.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                startActivity(new Intent(LicenseManagerListActivity.this, AddLicensePlateActivity.class));
            }
        });
    }

}
