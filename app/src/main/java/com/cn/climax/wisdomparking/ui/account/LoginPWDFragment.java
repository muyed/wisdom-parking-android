package com.cn.climax.wisdomparking.ui.account;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_login_with_pwd, null);
        etPassword = view.findViewById(R.id.etPassword);
        cbEyePassword = view.findViewById(R.id.cbEyePassword);
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
    }
}
