package com.cn.climax.wisdomparking.ui.setting;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.pay.alipay.AuthResult;
import com.cn.climax.i_carlib.pay.alipay.util.OrderInfoUtil2_0;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.PayConstant;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.base.help.RecyclerViewLayoutManager;
import com.cn.climax.wisdomparking.data.local.BaseLocalBean;
import com.cn.climax.wisdomparking.ui.main.device.ParkingSpacePayActivity;
import com.cn.climax.wisdomparking.ui.main.device.adapter.RVDevicePayAdapter;
import com.cn.climax.wisdomparking.ui.pay.bean.PayResult;
import com.cn.climax.wisdomparking.ui.setting.adapter.TagAdapter;
import com.cn.climax.wisdomparking.widget.flowtag.FlowTagLayout;
import com.cn.climax.wisdomparking.widget.flowtag.OnTagSelectListener;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class WalletMineActivity extends BaseSwipeBackActivity {

    @BindView(R.id.size_flow_layout)
    FlowTagLayout mMoneyFlowTagLayout;
    @BindView(R.id.tvPayDeposit)
    TextView tvPayDeposit;
    @BindView(R.id.rvPayMoneyTypeList)
    RecyclerView rvPayMoneyTypeList;

    String mDataJson;
    private TagAdapter<String> mMoneyTagAdapter;
    private RVDevicePayAdapter mAdapter;
    private BaseLocalBean checkBean = new BaseLocalBean();

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "我的钱包");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_wallet_mine;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        tvPayDeposit.setOnClickListener(new CommonClick());

        initView();
    }

    private void initView() {
        mMoneyTagAdapter = new TagAdapter<>(this);
        mMoneyFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mMoneyFlowTagLayout.setAdapter(mMoneyTagAdapter);
        mMoneyFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i : selectedList) {
                        sb.append(parent.getAdapter().getItem(i));
                        sb.append(":");
                    }
                    Snackbar.make(parent, "充值中心:" + sb.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        initMoneyData();

        RecyclerViewLayoutManager layoutManager = new RecyclerViewLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPayMoneyTypeList.setLayoutManager(layoutManager);
        mAdapter = new RVDevicePayAdapter(this);
        rvPayMoneyTypeList.setAdapter(mAdapter);

        mAdapter.setCheckPayWayListener(new RVDevicePayAdapter.OnCheckPayWayListener() {
            @Override
            public void pay(BaseLocalBean payBean) {
                checkBean = payBean;
            }
        });
    }

    private void initMoneyData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("50元");
        dataSource.add("100元");
        dataSource.add("200元");
        dataSource.add("500元");
        dataSource.add("1000元");
        mMoneyTagAdapter.onlyAddAll(dataSource);
    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.tvPayDeposit:
                    gotoPay(checkBean.getPayWay());
                    break;
            }
        }
    }

    private void gotoPay(int payWay) {
        switch (payWay) {
            case 0: //支付宝
                if (!SharedUtil.getInstance(this).get(ApiParamsKey.IS_AUTH_ALIPAY, false))
                    authAliPay();
                else
                    getAliPayOrder("CD20180418234539356044");
                break;
            case 1: //微信
                ToastUtils.show("微信支付");
                break;
        }
    }

    private void getAliPayOrder(String orderNo) {
        ApiManage.get(ApiHost.getInstance().getAliPayOrder() + orderNo)
                .tag(this)
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(s);
                                mDataJson = (String) jsonObject.get("data");
                                go2AliPay(mDataJson);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    private void go2AliPay(String mDataJson) {
        final String orderInfo = mDataJson;
        //异步处理
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(WalletMineActivity.this);
                //获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = PayConstant.SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void payDepositAmount(String depositAmount) {
        ApiManage.get(ApiHost.getInstance().payCash())
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("payCash_cache")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            ToastUtils.show("支付成功");
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PayConstant.SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户 authResult.getAuthCode()
                        SharedUtil.getInstance(WalletMineActivity.this).put(ApiParamsKey.IS_AUTH_ALIPAY, true);
                        go2AliPay(mDataJson);
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(WalletMineActivity.this, "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case PayConstant.SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //同步获取结果
                    String resultInfo = payResult.getResult();
                    Log.i("Pay", "Pay:" + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        payDepositAmount("299");
                        Toast.makeText(WalletMineActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(WalletMineActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };

    private void authAliPay() {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (PayConstant.RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PayConstant.PID, PayConstant.APPID, PayConstant.TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? PayConstant.RSA2_PRIVATE : PayConstant.RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(WalletMineActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = PayConstant.SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }
}
