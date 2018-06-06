package com.cn.climax.wisdomparking.ui.setting.utils;

import android.os.Bundle;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.LoginResponse;

import butterknife.BindView;

public class AuthenticatedInfoActivity extends BaseSwipeBackActivity {

    @BindView(R.id.etRealUserName)
    TextView etRealUserName;
    @BindView(R.id.etCertificateNo)
    TextView etCertificateNo;

    private LoginResponse mUserInfoBean;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "身份认证");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_authenticated_info;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        mUserInfoBean = (LoginResponse) getIntent().getSerializableExtra("account_bean");

        String hideName;
        if (mUserInfoBean.getRealName().length() == 2) {
            hideName = mUserInfoBean.getRealName().substring(0, 1) + "*";
        } else if (mUserInfoBean.getRealName().length() == 3) {
            hideName = mUserInfoBean.getRealName().substring(0, 1) + "**";
        } else {
            hideName = mUserInfoBean.getRealName().substring(0, 1) + "***";
        }
        etRealUserName.setText(hideName);

        String hideCardNo;
        hideCardNo = mUserInfoBean.getIdentityCard().substring(0, 3) + "***********" + mUserInfoBean.getIdentityCard().substring(mUserInfoBean.getIdentityCard().length() - 4);
        etCertificateNo.setText(hideCardNo);
    }

}
