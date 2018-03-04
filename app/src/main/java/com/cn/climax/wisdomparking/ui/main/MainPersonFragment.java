package com.cn.climax.wisdomparking.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.cn.climax.i_carlib.uiframework.bootstrap.BootstrapButton;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.fragment.BaseFragment;
import com.cn.climax.wisdomparking.ui.setting.MineGarageActivity;
import com.cn.climax.wisdomparking.ui.setting.ModifyProfileActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author：leo on 2018/3/3 19:44
 * email： leocheung4ever@gmail.com
 * description: 我的个人中心
 * what & why is modified:
 */

public class MainPersonFragment extends BaseFragment{

    @BindView(R.id.bbThemeSetting)
    BootstrapButton bbThemeSetting;
    @BindView(R.id.allGoToMyCars)
    LinearLayout allGoToMyCars;

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    protected void initUI(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {
        showContent(false);
    }

    @Override
    protected int initContentView() {
        return R.layout.fragment_main_person;
    }

    @OnClick({R.id.sdvAvatar, R.id.allGoToMyCars})
    void click(View view) {
        switch (view.getId()) {
            case R.id.sdvAvatar: //资料修改
                Intent modifyIntent = new Intent(getActivity(), ModifyProfileActivity.class);
                startActivity(modifyIntent);
                getActivity().overridePendingTransition(R.anim.pop_up_anim, R.anim.pop_no_anim);
                break;
            case R.id.allGoToMyCars: //我的车库
                Intent intent = new Intent(getActivity(), MineGarageActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.pop_up_anim, R.anim.pop_no_anim);
                break;
        }
    }
}
