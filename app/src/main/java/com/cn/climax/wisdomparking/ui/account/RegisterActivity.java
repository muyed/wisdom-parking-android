package com.cn.climax.wisdomparking.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.StringUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.i_carlib.util.VerificationUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.widget.NoUnderlineSpan;
import com.cn.climax.wisdomparking.widget.SmoothCheckBox;
import com.cn.climax.wisdomparking.widget.counttime.AwesomeCountDownTimer;
import com.lzy.okgo.callback.StringCallback;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends BaseSwipeBackActivity {

    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.etVerifyCode)
    EditText etVerifyCode;
    @BindView(R.id.checkBox)
    SmoothCheckBox checkBox;
    @BindView(R.id.btnVerify)
    AwesomeCountDownTimer btnVerify;
    @BindView(R.id.atvRegisterNote)
    TextView atvRegisterNote;
    @BindView(R.id.btnRegisterNext)
    Button btnRegisterNext;
    @BindView(R.id.btnRegister2Login)
    Button btnRegister2Login;

    private String mAccount;
    private String mVerifyCode;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "手机号注册");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initView();

        checkBox.setChecked(true);
        checkBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (!isChecked) {
                    btnRegisterNext.setBackgroundResource(R.drawable.activity_button_gray_border);
                } else {
                    btnRegisterNext.setBackgroundResource(R.drawable.activity_button_blue_border);
                }
            }
        });
        initClick();
    }

    private void initView() {
        SpannableStringBuilder regMeBuilder = new SpannableStringBuilder(atvRegisterNote.getText().toString());
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        NoUnderlineSpan regClickSpan = new NoUnderlineSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(new WeakReference<>(RegisterActivity.this).get(), AgreementActivity.class));
            }
        };
        regMeBuilder.setSpan(blueSpan, 8, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        regMeBuilder.setSpan(regClickSpan, 8, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        atvRegisterNote.setText(regMeBuilder);
        atvRegisterNote.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initClick() {
        btnVerify.setOnClickListener(new ForbidClick());
        btnRegisterNext.setOnClickListener(new ForbidClick());
        btnRegister2Login.setOnClickListener(new ForbidClick());
    }

    private class ForbidClick extends ForbidQuickClickListener {
        @Override
        protected void forbidClick(View view) {
            mAccount = etAccount.getText().toString();
            mVerifyCode = etVerifyCode.getText().toString();
            switch (view.getId()) {
                case R.id.btnVerify:
                    if (TextUtils.isEmpty(mAccount)) {
                        ToastUtils.show("请输入手机号码");
                    } else if (!StringUtil.isMobileNo(mAccount)) {
                        ToastUtils.show("手机号码不正确");
                    } else {
                        next2Code();
                    }
                    break;
                case R.id.btnRegisterNext:
                    if (TextUtils.isEmpty(mAccount)) {
                        ToastUtils.show("请输入手机号码");
                    } else if (!StringUtil.isMobileNo(mAccount)) {
                        ToastUtils.show("手机号码不正确");
                    } else if (TextUtils.isEmpty(mVerifyCode)) {
                        ToastUtils.show("请输入验证码");
                    } else {
                        startActivity(new Intent(RegisterActivity.this, PasswordSetActivity.class)
                                .putExtra("regist_code", mVerifyCode));
                    }
                    break;
                case R.id.btnRegister2Login:
                    finish();
                    break;
            }
        }
    }

    private void next2Code() {
        ApiManage.get(ApiHost.getInstance().getVerifyCodeR() + mAccount)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            btnVerify.setStartCountDownText("再次获取");
                            btnVerify.startCountDownTimer(60000, 1000);
                            ToastUtils.show("验证码已发送至您的手机，请注意查收");
                            SharedUtil.getInstance(RegisterActivity.this).put(ApiParamsKey.PHONE, mAccount);
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

}
