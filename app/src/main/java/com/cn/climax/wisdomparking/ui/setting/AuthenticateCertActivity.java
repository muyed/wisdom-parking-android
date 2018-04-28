package com.cn.climax.wisdomparking.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.i_carlib.util.VerificationUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.AuthResponse;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.wisdomparking.ui.main.community.AddCommunityActivity;
import com.cn.climax.wisdomparking.util.SharedPreferenceUtil;
import com.cn.climax.wisdomparking.util.StringUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class AuthenticateCertActivity extends BaseSwipeBackActivity {

    @BindView(R.id.etRealUserName)
    EditText etRealUserName;
    @BindView(R.id.etCertificateNo)
    EditText etCertificateNo;
    @BindView(R.id.tvConfirm2Submit)
    TextView tvConfirm2Submit;

    private String mCertificateName;
    private String mCertificateNo;

    @Override
    protected int initContentView() {
        return R.layout.activity_authenticate_cert;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "实名认证");
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        tvConfirm2Submit.setOnClickListener(new CommonClick());

        etRealUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCertificateName = s.toString();
            }
        });
        etCertificateNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCertificateNo = s.toString();
            }
        });
    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.tvConfirm2Submit:
                    if (TextUtils.isEmpty(mCertificateName)) {
                        ToastUtils.show("请输入您的姓名");
                    } else if (TextUtils.isEmpty(mCertificateNo)) {
                        ToastUtils.show("请输入您的身份证号码");
                    } else {
                        if (mCertificateNo.length() == 15 | mCertificateNo.length() == 18) {
                            if (mCertificateNo.length() == 15) {
                                if (VerificationUtil.is15Idcard(mCertificateNo)) {
                                    identification();
                                } else {
                                    ToastUtils.show("身份证不合法");
                                }
                            } else if (mCertificateNo.length() == 18) {
                                if (VerificationUtil.is18Idcard(mCertificateNo)) {
                                    identification();
                                } else {
                                    ToastUtils.show("身份证不合法");
                                }
                            }
                        } else {
                            ToastUtils.show("身份证不合法");
                        }
                    }
                    break;
            }
        }
    }

    private void identification() {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiParamsKey.REAL_NAME, mCertificateName);
        paramsMap.put(ApiParamsKey.IDENTITY_CARD, mCertificateNo);
        JSONObject json = new JSONObject(paramsMap);

        ApiManage.post(ApiHost.getInstance().idCardAuth())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<AuthResponse>(AuthenticateCertActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                        ToastUtils.show(msg);
                    }

                    @Override
                    protected void onExecuteSuccess(AuthResponse bean, Call call) {
                        if (bean.getCode() == 200) {
                            SharedUtil.getInstance(AuthenticateCertActivity.this).put(ApiParamsKey.IS_AUTH, true);
                            startActivity(new Intent(AuthenticateCertActivity.this, AddCommunityActivity.class));
                            finish();
                        } else {
                            if (bean.getCode() == 8) {
                                SharedUtil.getInstance(AuthenticateCertActivity.this).put(ApiParamsKey.IS_AUTH, true);
                            }
                        }
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {
                        ToastUtils.show(response.message());
                    }
                });
    }
}
