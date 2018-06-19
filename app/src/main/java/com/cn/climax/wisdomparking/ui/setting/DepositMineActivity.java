package com.cn.climax.wisdomparking.ui.setting;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.pay.alipay.AuthResult;
import com.cn.climax.i_carlib.pay.alipay.util.OrderInfoUtil2_0;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.i_carlib.weixin.WXPay;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.PayConstant;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.local.BaseLocalBean;
import com.cn.climax.wisdomparking.ui.main.licenseplate.adapter.RVDevicePayAdapter;
import com.cn.climax.wisdomparking.ui.pay.bean.PayResult;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class DepositMineActivity extends BaseSwipeBackActivity {

    @BindView(R.id.tvDepositAmount)
    TextView tvDepositAmount;
    @BindView(R.id.rvPayParkSpaceList)
    RecyclerView rvPayParkSpaceList;

    private RVDevicePayAdapter mAdapter;
    private BaseLocalBean checkBean = new BaseLocalBean();
    private String mOrderNo;

    private boolean isGo2PayAccount; //是否是过来缴纳账户押金
    private String mDepositAmount;
    private String mPayOrderNo;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        isGo2PayAccount = getIntent().getBooleanExtra("is_pay_account", false);
        super.setToolBar(isShowNavBack, isGo2PayAccount ? "缴纳账户押金" : "缴纳车位押金");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_deposit_mine;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        isGo2PayAccount = getIntent().getBooleanExtra("is_pay_account", false);
        mPayOrderNo = getIntent().getStringExtra("pay_order_no");
        if (isGo2PayAccount)
            mDepositAmount = getIntent().getStringExtra("pay_account_deposit");
        else
            mDepositAmount = getIntent().getStringExtra("pay_carport_deposit"); //// TODO: 2018/6/11 0011  
        tvDepositAmount.setText(mDepositAmount);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPayParkSpaceList.setLayoutManager(layoutManager);
        mAdapter = new RVDevicePayAdapter(this);
        rvPayParkSpaceList.setAdapter(mAdapter);

        mAdapter.setCheckPayWayListener(new RVDevicePayAdapter.OnCheckPayWayListener() {
            @Override
            public void pay(BaseLocalBean payBean) {
                checkBean = payBean;
            }
        });
    }

    @OnClick({R.id.tvNextStep2})
    void click(View view) {
        switch (view.getId()) {
            case R.id.tvNextStep2:
                if (!TextUtils.isEmpty(mPayOrderNo)) {
                    mOrderNo = mPayOrderNo;
                    gotoPay(checkBean.getPayWay());
                } else {
                    if (isGo2PayAccount)
                        getPayAccount();
                    else
                        getPayCarport();
                }
                break;
        }
    }

    /**
     * 先获取订单号 - 缴纳账户押金
     */
    private void getPayAccount() {
        ApiManage.get(ApiHost.getInstance().payCash())
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("payCash_cache")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("onSuccess: ", s);
                        JSONObject json;
                        try {
                            json = new JSONObject(s);
                            int code = Integer.parseInt(String.valueOf(json.get("code")));
                            String errMsg = String.valueOf(json.get("errMsg"));
                            if (code == 200) {
                                mOrderNo = String.valueOf(json.get("data"));
                                gotoPay(checkBean.getPayWay());
                            } else {
                                ToastUtils.show(errMsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 先获取订单号 - 缴纳车位押金
     */
    private void getPayCarport() {
        ApiManage.get(ApiHost.getInstance().payCash())
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("payCash_cache")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("onSuccess: ", s);
                        JSONObject json;
                        try {
                            json = new JSONObject(s);
                            int code = Integer.parseInt(String.valueOf(json.get("code")));
                            String errMsg = String.valueOf(json.get("errMsg"));
                            if (code == 200) {
                                mOrderNo = String.valueOf(json.get("data"));
                                gotoPay(checkBean.getPayWay());
                            } else {
                                ToastUtils.show(errMsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void gotoPay(int payWay) {
        switch (payWay) {
            case 0: //支付宝
                if (!SharedUtil.getInstance(this).get(ApiParamsKey.IS_AUTH_ALIPAY, false))
                    authAliPay();
                else
                    go2AliPay();
                break;
            case 1: //微信
                go2WeChatPay();
                break;
        }
    }

    private void go2WeChatPay() {
        ApiManage.get(ApiHost.getInstance().payByWeChat() + mOrderNo)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            Log.e("go2WeChatPay: ", s);
                            JSONObject json;
                            try {
                                json = new JSONObject(s);
                                String orderInfoParam = String.valueOf(json.get("data"));
                                doWXPay(orderInfoParam);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    /**
     * 微信支付
     *
     * @param pay_param 支付服务生成的支付参数
     */
    private void doWXPay(String pay_param) {
        String wx_appid = "wx39fab7de09774fbd";     //替换为自己的appid = wx39fab7de09774fbd
        WXPay.init(getApplicationContext(), wx_appid);      //要在支付前调用
        WXPay.getInstance().doPay(pay_param, new WXPay.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
                SharedUtil.getInstance(DepositMineActivity.this).put(ApiParamsKey.IS_WITHDRAWED_DEPOSIT, true);
                finish();
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case WXPay.NO_OR_LOW_WX:
                        Toast.makeText(getApplication(), "未安装微信或微信版本过低", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY_PARAM:
                        Toast.makeText(getApplication(), "参数错误", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY:
                        Toast.makeText(getApplication(), "支付失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), "支付取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

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
                AuthTask authTask = new AuthTask(DepositMineActivity.this);
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

    private void go2AliPay() {
        ApiManage.get(ApiHost.getInstance().payByAliPay() + mOrderNo)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            Log.e("go2WeChatPay: ", s);
                            JSONObject json;
                            try {
                                json = new JSONObject(s);
                                String orderInfoParam = String.valueOf(json.get("data"));
                                doALIPay(orderInfoParam);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    private void doALIPay(String orderInfoParam) {
        //订单信息
        final String orderInfo = orderInfoParam;
        //异步处理
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(DepositMineActivity.this);
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
                        SharedUtil.getInstance(DepositMineActivity.this).put(ApiParamsKey.IS_AUTH_ALIPAY, true);
                        go2AliPay();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(DepositMineActivity.this, "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DepositMineActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        SharedUtil.getInstance(DepositMineActivity.this).put(ApiParamsKey.IS_WITHDRAWED_DEPOSIT, true);
                        finish();
                    } else {
                        Toast.makeText(DepositMineActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };
}
