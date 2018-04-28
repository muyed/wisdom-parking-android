package com.cn.climax.wisdomparking.ui.main.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.local.CarLockParkingBean;
import com.cn.climax.wisdomparking.ui.main.device.ReleaseLockActivity;
import com.cn.climax.wisdomparking.ui.main.share.PublishShareParkingActivity;
import com.cn.climax.wisdomparking.widget.cyclepager.view.CyclePager;

import java.util.ArrayList;

import butterknife.BindView;

public class IdentifyCommunityActivity extends BaseSwipeBackActivity {

    @BindView(R.id.lock_viewPager)
    CyclePager lockViewPager;
    @BindView(R.id.ll_point)
    LinearLayout ll_point;
    @BindView(R.id.llSkip2Share) LinearLayout llSkip2Share;
    @BindView(R.id.llReleaseLock) LinearLayout llReleaseLock;

    private ArrayList<CarLockParkingBean> lockList = new ArrayList<>();

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "小区认证");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_identify_community;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initLockPager();
        initClick();
    }

    private void initClick() {
        llSkip2Share.setOnClickListener(new CommonClick());
        llReleaseLock.setOnClickListener(new CommonClick());
    }

    private void initLockPager() {
        CarLockParkingBean parkingBean1 = new CarLockParkingBean();
        parkingBean1.setLockName("车位锁一");
        parkingBean1.setLockRemark("备注一的名称");
        lockList.add(parkingBean1);
        CarLockParkingBean parkingBean2 = new CarLockParkingBean();
        parkingBean2.setLockName("车位锁二");
        parkingBean2.setLockRemark("备注二的名称");
        lockList.add(parkingBean2);
        CarLockParkingBean parkingBean3 = new CarLockParkingBean();
        parkingBean3.setLockName("车位锁三");
        parkingBean3.setLockRemark("备注三的名称");
        lockList.add(parkingBean3);

        lockViewPager.addPoints(mContext, R.drawable.bg_pointer, ll_point, lockList.size());
        lockViewPager.setImages(mContext, lockList, R.layout.view_pager_lock_item_layout, new CyclePager.OnItemInitLisenter() {
            @Override
            public void initItemView(View view, int position) {
                TextView tvCarSpaceLockNo = (TextView) view.findViewById(R.id.tvCarSpaceLockNo);
                TextView tvCarSpaceLockRemark = (TextView) view.findViewById(R.id.tvCarSpaceLockRemark);
                tvCarSpaceLockNo.setText(lockList.get(position).getLockName());
                tvCarSpaceLockRemark.setText(lockList.get(position).getLockRemark());
            }

            @Override
            public void onItemClick(int position) {
            }

            @Override
            public void onItemVisible(int position) {
            }
        }, 6);
    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.llSkip2Share: //共享车位锁
                    startActivity(new Intent(IdentifyCommunityActivity.this, PublishShareParkingActivity.class));
                    break;
                case R.id.llReleaseLock: //解锁车位锁
                    startActivity(new Intent(IdentifyCommunityActivity.this, ReleaseLockActivity.class));
                    break;
            }
        }
    }
}
