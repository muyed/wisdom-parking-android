package com.cn.climax.wisdomparking.ui.main.community;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.base.activity.CustomSwipeBackActivity;
import com.cn.climax.wisdomparking.widget.tablayout.SegmentTabLayout;
import com.cn.climax.wisdomparking.widget.tablayout.SimpleTimeCardFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class AppointmentCommunityActivity extends CustomSwipeBackActivity {

    @BindView(R.id.stlTimeTab)
    SegmentTabLayout stlTimeTab;

    private ArrayList<Fragment> mFragments2 = new ArrayList<>();
    private String[] mTitles = {"公共车位", "小区车位"};

    @Override
    protected int initContentView() {
        return R.layout.activity_appointment_community;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initDayPicker();
        for (String title : mTitles) {
            mFragments2.add(SimpleTimeCardFragment.getInstance("Switch Fragment " + title));
        }
        stlTimeTab.setTabData(mTitles, this, R.id.fl_change, mFragments2);
    }

    private void initDayPicker() {
        
    }

}
