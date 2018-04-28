package com.cn.climax.wisdomparking.ui.pay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.pay.bean.PayResult;
import com.cn.climax.i_carlib.pay.alipay.util.OrderInfoUtil2_0;

import java.util.Map;

import butterknife.OnClick;

public class PayOrderActivity extends BaseSwipeBackActivity {

    private static final int SDK_PAY_FLAG = 1001;
    private String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCSOszTz7rj0RWGtSe4gEN5bVhYmvItDiyumnIFlIrzfBScsdV6Zap66UDx9j8lf82dLqW+b5wmVHfc0lGv34HJqdaZe6liV8Aam12D97/j0FlSkYvztk0N5XEAvsG+oURLAEvfosj2ruJy/phHnGsZ5/zhT2C5JciFgeIUdh8IPkQ8FQaIzycAi9Ad1NfOaL1p+iAIrfIqQLhpAavZ0DcR4nQKbAaVDon58YOzDja0jeo/Uq7DpVi2MO5e8TIigHG3KV25RZKWVmIzUg/OJOCiWDVcaloTA3XE2ejPcRegg2GxYFQcxOa+BU2kPh8cRT5J2aApBWhzRSoj9LbGiS5nAgMBAAECggEARfDd20l2SFTPCPlIoFSo8UE9ilPCFY68DF9OviCIPWFRsojUZJCP8+3w5Eo1dcglHlMbb3SOqT6pffMSox4yQ7R6MjsVjzYNaix5lA9BtsMGlhmLaOv++vCNFiAnJviXY5AmujZwDJ0lLn9bcpT8HQ36xAFlb+kEeebfrY251gTHgK5OSs/HZJSbqKaqTFf4Xzgtgbdf5KVyj8rghpVWDw4E6fDfh7J1wbvUaVi8NUanXuez8mFJFwuHIn5N2k2vjwDwFDFnOTzyLutXmaDNYf/qygEo9gI48Y8NY67cO9rEWmc/6/rAPNypG8nGK4pRcxh8PGEc4SGiYq44luQYsQKBgQDdAx+PQKcU85yanwZ2WWEdWL4IujD3NRdCVbckfFMQszRnlxG8RIWgwRbwVTMl8lb1H8R5pI1dNciLEZu8NMPoz88YXuIk8WA4Op5Bml5OsydjaqSj0BhLijeF+HIEAKjjzcg5jHKHPxHzzf4XS7Jt2KSCQPFEAE0H0WOdOQKoDQKBgQCpYP6fC6v7FQ1kVJjQeEtbFHKwZ4VQUWu3uil7m/eEg8ooznK64fR1FkdK2ebYhW3yvfwcYAK0K0mD+DTfMfdzto83YOCM1jToKeMrWJrq9qUHX3ys8Qs4cPXtBJkv3BnMycYMU7OS5s9eJTeJiyaYNavWJR+dYfpX1F3iBmw/QwKBgQDRppzRbysPnOf5ZNDkg3S0OfJKpRS97AbcTiMk4SqKtPQJMC0KwpVgIMp5wNh8Lp5+eFwQaCARQ2uxQErTBdqxhi+Vlqw/DxmPPUvSP3Umgpvims8C9euV/M8ersa5NUcAkbxVmv0pfpKxC770qkHgIZosVR6IuyBXQjGUevgiIQKBgAbC0GcY2CyVkotGtTLdGmTwVAOmnjOujFYxICbG+wJXIGKwmvPKwS9LTF04hXZvHQxrePzI+FKox6504edm3zHHP8A+Jbh5dHMv+NlqGAT5A21f6mIc3Q3LtIVZgaPfbUg3ZahoTuq2O+DOK4FaBJhezBUoNjLaDlwjJfw3tWpxAoGAQCw8wlE8zxo3dOQutCImj/2kSiUPcgTDd4WJHgeMWEmP1NsWd8bRl7b4S1RUzaMfZbVtgXFuQBm7MAUkSbbW4IpBttz8Vv4FfvVKx7ZESXgsLT9klFZhGk7Hoq/xmWdihgD2OqHGcHxn8Kz2oguDXPAuW9JGMZFlN1sKUPBxqj4=";
    public static final String APPID = "2018022602278947";

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "支付");
    }

    private int mPayWay;

    @Override
    protected int initContentView() {
        return R.layout.activity_pay_order;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        mPayWay = getIntent().getIntExtra("check_pay_way", 0);
        initPay();
    }

    private void initPay() {
        
    }
    
    @OnClick({R.id.tvSkip2OnLine, R.id.tvSkip2AliPay, R.id.tvSkip2WeChat})
    void click(View view){
        switch (view.getId()){
            case R.id.tvSkip2OnLine:
                gotoPay();
                break;
            case R.id.tvSkip2AliPay:
                break;
            case R.id.tvSkip2WeChat:
                break;
        }
    }

    private void gotoPay() {
        //秘钥验证的类型 true:RSA2 false:RSA
        boolean rsa = false;
        //构造支付订单参数列表
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa);
        //构造支付订单参数信息
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        //对支付参数信息进行签名
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, rsa);
        //订单信息
        final String orderInfo = orderParam + "&" + sign;
        //异步处理
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(PayOrderActivity.this);
                //获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
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
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //同步获取结果
                    String resultInfo = payResult.getResult();
                    Log.i("Pay", "Pay:" + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PayOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

}
