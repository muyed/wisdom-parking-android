package com.cn.climax.wisdomparking.ui.main.carport;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.widget.xrecyclerview.XRecyclerView;

import butterknife.BindView;

public class ParkingSpaceSearchActivity extends BaseSwipeBackActivity {

    @BindView(R.id.atvLeftTitle)
    TextView atvLeftTitle;
    @BindView(R.id.allToLocation)
    LinearLayout allToLocation;
    @BindView(R.id.rvSearchResultList)
    XRecyclerView rvSearchResultList;

    @Override
    protected int initContentView() {
        return R.layout.activity_parking_space_search;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        atvLeftTitle.setText("地图");
        initView();
        initClick();
    }

    private void initView() {

    }

    private void initClick() {
        allToLocation.setOnClickListener(new ForbidClick());
    }

    private class ForbidClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.allToLocation: //进入地图

                    break;
            }
        }
    }
}
