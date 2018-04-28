package com.cn.climax.wisdomparking.ui.account;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.util.EditTextUtils;

/**
 * author：leo on 2018/2/27 00:10
 * email： leocheung4ever@gmail.com
 * description: 短信验证码登录碎片
 * what & why is modified:
 */

public class LoginPWDFragment extends Fragment {

    private EditText etPassword;
    private CheckBox cbEyePassword;
    private LinearLayout atvFindMe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_login_with_pwd, null);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        cbEyePassword = (CheckBox) view.findViewById(R.id.cbEyePassword);
        atvFindMe = (LinearLayout) view.findViewById(R.id.atvFindMe);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cbEyePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextUtils.getInstance().showOrHide(etPassword);
            }
        });

        atvFindMe.setOnClickListener(new CommonClick());
    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.atvFindMe:
                    startActivity(new Intent(getContext(), RetrievePwdActivity.class));
                    break;
            }
        }
    }
}
