package com.cn.climax.wisdomparking.ui.account;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.uiframework.bootstrap.BootstrapButton;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.StringUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.widget.counttime.AwesomeCountDownTimer;
import com.lzy.okgo.callback.StringCallback;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * author：leo on 2018/2/27 00:10
 * email： leocheung4ever@gmail.com
 * description: 短信验证码登录碎片
 * what & why is modified:
 */

public class LoginSNSFragment extends Fragment {

    private AwesomeCountDownTimer btnVerify;
    private EditText etAccount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_login_with_sns, null);
        etAccount = (EditText) view.findViewById(R.id.etAccount);
        btnVerify = (AwesomeCountDownTimer) view.findViewById(R.id.btnVerify);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnVerify.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                getSnsCode();
            }
        });
    }

    private void getSnsCode() {
        final String mMobileNo = etAccount.getText().toString();
        if (TextUtils.isEmpty(mMobileNo)) {
            ToastUtils.show("请输入手机号码");
        } else if (!StringUtil.isMobileNo(mMobileNo)) {
            ToastUtils.show("请输入正确手机号码");
        } else {
            ApiManage.get(ApiHost.getInstance().getVerifyCodeL() + mMobileNo)
                    .tag(this)// 请求的 tag, 主要用于取消对应的请求
                    .cacheKey("login_sns")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            ToastUtils.show("验证码已发送至您的手机，请注意查收");
                            btnVerify.setStartCountDownText("再次获取");
                            btnVerify.startCountDownTimer(60000, 1000);
                            SharedUtil.getInstance(getActivity()).put(ApiParamsKey.PHONE, mMobileNo);
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        btnVerify.onDestroy();
    }
}
