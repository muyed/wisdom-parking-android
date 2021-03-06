package com.cn.climax.wisdomparking.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.Core;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.base.help.CustomLinearLayoutManager;
import com.cn.climax.wisdomparking.data.response.LoginResponse;
import com.cn.climax.wisdomparking.data.response.ParkingSpaceMineBean;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.wisdomparking.ui.setting.adapter.DepositReturnAdapter;
import com.cn.climax.wisdomparking.ui.setting.bank.WithDrawalActivity;
import com.lzy.okgo.callback.StringCallback;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class DepositReturnActivity extends BaseSwipeBackActivity {

    @BindView(R.id.rvDepositListView)
    RecyclerView rvDepositListView;
    @BindView(R.id.llClickPayStyle)
    LinearLayout llClickPayStyle;
    @BindView(R.id.tvSelectedCommunity)
    TextView tvSelectedCommunity;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvGoToDeposit)
    TextView tvGoToDeposit;
    @BindView(R.id.llNoDepositArea)
    LinearLayout llNoDepositArea;

    @BindView(R.id.llEmptyView)
    LinearLayout llEmptyView;
    @BindView(R.id.tvNoCarSpaceHint)
    TextView tvNoCarSpaceHint;

    private DepositReturnAdapter adapter;
    private List<ParkingSpaceMineBean> mParkingSpaceMineBeanList;
    private LoginResponse.AccountBean mAccountBean;
    private String mAccountAmount;
    private String mDepositAmount;

    @Override
    protected int initContentView() {
        return R.layout.activity_deposit_return;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "押金");
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        loginEvent();
    }

    private void loginEvent() {
        HashMap<String, String> httpParams = new HashMap<>();
        httpParams.put(ApiParamsKey.USER_NAME, SharedUtil.getInstance(DepositReturnActivity.this).get(ApiParamsKey.USER_NAME));
        httpParams.put(ApiParamsKey.PASSWORD, SharedUtil.getInstance(DepositReturnActivity.this).get(ApiParamsKey.PASSWORD));
        httpParams.put(ApiParamsKey.TYPE, "1");
        JSONObject json = new JSONObject(httpParams);

        ApiManage.post(ApiHost.getInstance().login())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<LoginResponse>(DepositReturnActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                        ToastUtils.show(msg);
                    }

                    @Override
                    protected void onExecuteSuccess(LoginResponse bean, Call call) {
                        SharedUtil.getInstance(DepositReturnActivity.this).put("is_login_success", true);
                        SharedUtil.getInstance(DepositReturnActivity.this).put(ApiParamsKey.REAL_NAME, bean.getRealName());
                        SharedUtil.getInstance(DepositReturnActivity.this).put(ApiParamsKey.IS_AUTH, !TextUtils.isEmpty(bean.getRealName()));
                        SharedUtil.getInstance(DepositReturnActivity.this).put(ApiParamsKey.IS_AUTH_COMMUNITY, bean != null && bean.getCommunityList() != null && bean.getCommunityList().size() > 0);
                        SharedUtil.getInstance(DepositReturnActivity.this).put(ApiParamsKey.IS_AUTH_PARKING_SPACE, bean != null && bean.getUserCarportList() != null && bean.getUserCarportList().size() > 0);
                        SharedUtil.getInstance(DepositReturnActivity.this).put(ApiParamsKey.ACCOUNT_DEPOSIT_AMOUNT, String.valueOf(bean.getAccountCashConf()));
                        SharedUtil.getInstance(DepositReturnActivity.this).put(ApiParamsKey.CARPORT_DEPOSIT_AMOUNT, String.valueOf(bean.getCarportCashConf()));
                        initDepositView(bean);
                        getMyCarport();
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {
                        ToastUtils.show(response.message());
                    }

                    @Override
                    protected boolean setDialogShow() {
                        return false;
                    }
                });
    }

    private void initDepositView(LoginResponse bean) {
        mAccountBean = bean.getAccount();
        mAccountAmount = String.valueOf(bean.getAccountCashConf());
        mDepositAmount = String.valueOf(bean.getCarportCashConf());

        if (String.valueOf(mAccountBean.getCash()).equals("0.00")
                || mAccountBean.getCash() == 0.00d
                || mAccountBean.getCash() == 0.0d
                || mAccountBean.getCash() == 0d) {
            tvTitle.setVisibility(View.GONE);
            llNoDepositArea.setVisibility(View.VISIBLE);
            tvGoToDeposit.setVisibility(View.VISIBLE);
            tvGoToDeposit.setText("交押金");
            tvGoToDeposit.setBackgroundResource(R.drawable.activity_button_blue_border);
            tvGoToDeposit.setOnClickListener(new ForbidQuickClickListener() { //缴纳押金 
                @Override
                protected void forbidClick(View view) {
                    String deposit = mAccountAmount;
                    startActivityForResult(new Intent(DepositReturnActivity.this, DepositMineActivity.class)
                            .putExtra("is_pay_account", true)
                            .putExtra("pay_account_deposit", deposit), 199);
                }
            });
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            llNoDepositArea.setVisibility(View.GONE);
            tvGoToDeposit.setVisibility(View.VISIBLE);
            tvGoToDeposit.setText("退押金");
            tvGoToDeposit.setBackgroundResource(R.drawable.activity_button_red_border);
            tvGoToDeposit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog4Withdraw();
                }
            });
        }
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(this);
        rvDepositListView.setLayoutManager(linearLayoutManager);
        adapter = new DepositReturnAdapter(this, mDepositAmount);
        rvDepositListView.setAdapter(adapter);
    }

    private void showDialog4Withdraw() {
        final View contentView = Core.getInstances().getCurrentActivity().getLayoutInflater().inflate(R.layout.dialog_hint_withdraw, null);
        ViewHolder DialogViewHolder = new ViewHolder(contentView);
        final DialogPlus dialogPlus = DialogPlus.newDialog(Core.getInstances().getCurrentContext())
                .setContentHolder(DialogViewHolder)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogPlus dialog) {
                    }
                })
                .setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {
                    }
                })
                .create();
        dialogPlus.show();

        contentView.findViewById(R.id.tvConfirmPreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlus.dismiss();
            }
        });
        contentView.findViewById(R.id.tvWithdrawPreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, WithDrawalActivity.class)
                        .putExtra("is_withdraw_account", true)
                        .putExtra("deposit_account_amount", mDepositAmount)
                        .putExtra("deposit_account_bean", mAccountBean));
                dialogPlus.dismiss();
            }
        });
    }

    private void getMyCarport() {
        ApiManage.get(ApiHost.getInstance().getMyCarport())
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                mParkingSpaceMineBeanList = com.alibaba.fastjson.JSONObject.parseArray(String.valueOf(json.get("data")), ParkingSpaceMineBean.class);
                                if (mParkingSpaceMineBeanList != null && mParkingSpaceMineBeanList.size() > 0) {
                                    llEmptyView.setVisibility(View.GONE);
                                    rvDepositListView.setVisibility(View.VISIBLE);

                                    adapter.setData(mParkingSpaceMineBeanList);
                                } else {
                                    llEmptyView.setVisibility(View.VISIBLE);
                                    rvDepositListView.setVisibility(View.GONE);
                                    tvNoCarSpaceHint.setText("车位锁认证中，请耐心等待后台审核");
                                    
//                                    tvNoCarSpaceHint.setText("还没有绑定车位锁，快去绑定吧");
//                                    SpannableStringBuilder regTipBuilder = new SpannableStringBuilder(tvNoCarSpaceHint.getText().toString());
//                                    ForegroundColorSpan blueSpan = new ForegroundColorSpan(ContextCompat.getColor(DepositReturnActivity.this, R.color.colorPrimary));
//                                    regTipBuilder.setSpan(blueSpan, 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                    BackgroundColorSpan whiteSpan = new BackgroundColorSpan(ContextCompat.getColor(DepositReturnActivity.this, R.color.white));
//                                    regTipBuilder.setSpan(whiteSpan, 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                    regTipBuilder.setSpan(new AbsoluteSizeSpan(32), 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                    NoUnderlineSpan clickSpan = new NoUnderlineSpan() {
//                                        @Override
//                                        public void onClick(View widget) {
//                                            startActivityForResult(new Intent(DepositReturnActivity.this, AddDeviceActivity.class), 99);
//                                        }
//                                    };
//                                    regTipBuilder.setSpan(clickSpan, 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                    tvNoCarSpaceHint.setText(regTipBuilder);
//                                    tvNoCarSpaceHint.setMovementMethod(LinkMovementMethod.getInstance());
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

}
