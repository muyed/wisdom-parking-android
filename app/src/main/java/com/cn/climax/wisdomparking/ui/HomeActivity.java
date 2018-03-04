package com.cn.climax.wisdomparking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.cn.climax.i_carlib.uiframework.navigation.SmartNavigationBar;
import com.cn.climax.i_carlib.uiframework.navigation.SmartNavigationItem;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseActivity;
import com.cn.climax.wisdomparking.ui.account.LoginActivity;
import com.cn.climax.wisdomparking.ui.main.MainHomeFragment;
import com.cn.climax.wisdomparking.ui.main.MainPersonFragment;
import com.cn.climax.wisdomparking.widget.UnScrollableViewPager;

import java.util.ArrayList;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.vp_main)
    UnScrollableViewPager vp_main;
    @BindView(R.id.bottom_navigation_bar)
    SmartNavigationBar bottom_navigation_bar;

    private long exitTime = 0;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int initContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isLoginSuccess = SharedUtil.getInstance(HomeActivity.this).get("is_login_success", false);
        if (!isLoginSuccess) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initNavigation();
    }

    //初始化底部导航栏 bottom_navigation_bar
    private void initNavigation() {
        bottom_navigation_bar
                .addItem(new SmartNavigationItem(R.drawable.ic_nav_home, "车行宝典").setInActiveIconResource(R.drawable.ic_nav_home_inactive))
                .addItem(new SmartNavigationItem(R.drawable.ic_nav_drive, "车行汇").setInActiveIconResource(R.drawable.ic_nav_drive_inactive))
                .addItem(new SmartNavigationItem(R.drawable.ic_nav_me, "我的").setInActiveIconResource(R.drawable.ic_nav_me_inactive))
                .initialise();
        bottom_navigation_bar.setTabSelectedListener(new SmartNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                vp_main.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });

        mFragmentList.add(new MainHomeFragment());
        mFragmentList.add(new MainHomeFragment());
        mFragmentList.add(new MainPersonFragment());

        vp_main.setScrollble(false);
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottom_navigation_bar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        vp_main.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 1000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                SharedUtil.getInstance(getApplicationContext()).put("last_launch", System.currentTimeMillis());
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
