package com.cn.climax.wisdomparking.ui.setting.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.BankCardMineBean;
import com.cn.climax.wisdomparking.data.response.LoginResponse;
import com.cn.climax.wisdomparking.data.response.ParkingSpaceMineBean;
import com.cn.climax.wisdomparking.data.response.WithDrawalResponse;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.wisdomparking.ui.setting.utils.BankManager;
import com.cn.climax.wisdomparking.widget.pay.Keyboard;
import com.cn.climax.wisdomparking.widget.pay.PayEditText;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class WithDrawalActivity extends BaseSwipeBackActivity {

    @BindView(R.id.PayEditText_pay)
    PayEditText payEditText;
    @BindView(R.id.KeyboardView_pay)
    Keyboard keyboard;
    @BindView(R.id.etInputWithDrawalAmount)
    EditText etInputWithDrawalAmount;
    @BindView(R.id.btnBindCardConfirm)
    Button btnBindCardConfirm; //确认提现

    @BindView(R.id.llPwdKeyboard)
    LinearLayout llPwdKeyboard;
    @BindView(R.id.llShowBankArea)
    LinearLayout llShowBankArea;
    @BindView(R.id.llAddBankArea)
    LinearLayout llAddBankArea;
    @BindView(R.id.ivWithDrawalBankLogo)
    ImageView ivWithDrawalBankLogo;
    @BindView(R.id.tvWithDrawalBankName)
    TextView tvWithDrawalBankName;
    @BindView(R.id.tvWithDrawalBankNo)
    TextView tvWithDrawalBankNo;

    private static final String[] KEY = new String[]{
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "", "0", "<<"
    };
    private BankCardMineBean mCardBean;
    private String mAmount;

    private boolean isFromCarport;
    private String mCarportAmount;
    private ParkingSpaceMineBean mCarportBean;
    private boolean isFromAccount;
    private String mAccountAmount;
    private LoginResponse.AccountBean mAccountBean;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        isFromCarport = getIntent().getBooleanExtra("is_withdraw_carport", false);
        isFromAccount = getIntent().getBooleanExtra("is_withdraw_account", false);
        super.setToolBar(isShowNavBack, isFromCarport || isFromAccount ? "退押金" : "提现");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_with_drawal;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
//        hideSystemSofeKeyboard(WithDrawalActivity.this, etInputWithDrawalAmount);
        isFromCarport = getIntent().getBooleanExtra("is_withdraw_carport", false);
        mCarportAmount = getIntent().getStringExtra("deposit_carport_amount");
        mCarportBean = (ParkingSpaceMineBean) getIntent().getSerializableExtra("deposit_carport_bean");

        isFromAccount = getIntent().getBooleanExtra("is_withdraw_account", false);
        mAccountAmount = getIntent().getStringExtra("deposit_account_amount");
        mAccountBean = (LoginResponse.AccountBean) getIntent().getSerializableExtra("deposit_account_bean");

        initView();
        setSubView();
        initEvent();
    }

    private void initView() {
        llShowBankArea.setVisibility(View.GONE);
        llAddBankArea.setVisibility(View.VISIBLE);
        llAddBankArea.setOnClickListener(new CommonClick());
        llShowBankArea.setOnClickListener(new CommonClick());
        btnBindCardConfirm.setOnClickListener(new CommonClick());

        etInputWithDrawalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mAmount = s.toString();
                } else {
                    mAmount = null;
                }
            }
        });

        if (isFromCarport) {
            etInputWithDrawalAmount.setText(mCarportAmount);
            etInputWithDrawalAmount.setTextColor(ContextCompat.getColor(WithDrawalActivity.this, R.color.text_darker_color));
            etInputWithDrawalAmount.setEnabled(false);
        }
        if (isFromAccount) {
            etInputWithDrawalAmount.setText(mAccountAmount);
            etInputWithDrawalAmount.setTextColor(ContextCompat.getColor(WithDrawalActivity.this, R.color.text_darker_color));
            etInputWithDrawalAmount.setEnabled(false);
        }
    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.btnBindCardConfirm:
//                    llPwdKeyboard.setVisibility(View.VISIBLE);
//                    toastPayDialog();
                    if (isFromCarport) {
                        withDrawalCarport();
                    } else if (isFromAccount) {
                        withDrawalAccount();
                    } else {
                        if (TextUtils.isEmpty(mAmount)) {
                            ToastUtils.show("请填写金额");
                            return;
                        }
                        withDrawal();
                    }
                    break;
                case R.id.llAddBankArea:
                    startActivityForResult(new Intent(WithDrawalActivity.this, BankCardListActivity.class).putExtra("is_enableSelect", true), 99);
                    break;
                case R.id.llShowBankArea:
                    startActivityForResult(new Intent(WithDrawalActivity.this, BankCardListActivity.class).putExtra("is_enableSelect", true), 99);
                    break;
            }
        }
    }

    private void withDrawalAccount() {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiParamsKey.ID, mCardBean.getId() + "");
        JSONObject json = new JSONObject(paramsMap);
        ApiManage.post(ApiHost.getInstance().withdrawCash())
                .upJson(json.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                int code = Integer.parseInt(String.valueOf(json.get("code")));
                                String errMsg = String.valueOf(json.get("errMsg"));
                                if (code == 200) {

                                    ToastUtils.show("提现成功");
                                    finish();
                                } else {
                                    ToastUtils.show(errMsg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    private void withDrawalCarport() {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiParamsKey.BANK_ID, mCardBean.getId() + "");
        paramsMap.put(ApiParamsKey.ID, mCarportBean.getCarportId() + "");
        JSONObject json = new JSONObject(paramsMap);

        ApiManage.post(ApiHost.getInstance().withdrawCarport())
                .upJson(json.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                int code = Integer.parseInt(String.valueOf(json.get("code")));
                                String errMsg = String.valueOf(json.get("errMsg"));
                                if (code == 200) {
                                    ToastUtils.show("提现成功");
                                    finish();
                                } else {
                                    ToastUtils.show(errMsg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    private void withDrawal() {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiParamsKey.BANK_ID, mCardBean.getId() + "");
        paramsMap.put(ApiParamsKey.BANK_WITH_AMOUNT, mAmount);
        JSONObject json = new JSONObject(paramsMap);

        ApiManage.post(ApiHost.getInstance().withdrawBalance())
                .upJson(json.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                int code = Integer.parseInt(String.valueOf(json.get("code")));
                                String errMsg = String.valueOf(json.get("errMsg"));
                                if (code == 200) {
                                    ToastUtils.show("提现成功");
                                    finish();
                                } else {
                                    ToastUtils.show(errMsg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 99 && resultCode == RESULT_OK) {
            if (data != null) {
                mCardBean = (BankCardMineBean) data.getSerializableExtra("card_bean");
                llShowBankArea.setVisibility(View.VISIBLE);
                llAddBankArea.setVisibility(View.GONE);
                ivWithDrawalBankLogo.setImageResource(BankManager.getImageResId(mContext, mCardBean.getBankName()));
                tvWithDrawalBankName.setText(mCardBean.getBankName());
                tvWithDrawalBankNo.setText("尾号" + mCardBean.getBankAccount().substring(mCardBean.getBankAccount().length() - 4) + "储蓄卡");
            }
        }
    }

    private void initEvent() {
        keyboard.setOnClickKeyboardListener(new Keyboard.OnClickKeyboardListener() {
            @Override
            public void onKeyClick(int position, String value) {
                if (position < 11 && position != 9) {
                    payEditText.add(value);
                } else if (position == 9) {
                    payEditText.remove();
                } else if (position == 11) {
                    //当点击完成的时候，也可以通过payEditText.getText()获取密码，此时不应该注册OnInputFinishedListener接口
//                    Toast.makeText(getApplication(), "您的密码是：" + payEditText.getText(), Toast.LENGTH_SHORT).show();
//                    finish();
                }
            }
        });

        /**
         * 当密码输入完成时的回调
         */
        payEditText.setOnInputFinishedListener(new PayEditText.OnInputFinishedListener() {
            @Override
            public void onInputFinished(String password) {
                Toast.makeText(getApplication(), "您的密码是：" + password, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSubView() {
        //设置键盘
        keyboard.setKeyboardKeys(KEY);
    }

    private void toastPayDialog() {

    }

    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public static void hideSystemSofeKeyboard(Context context, EditText editText) {
        Log.i(">>>>>", "hide");
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
        // 如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
