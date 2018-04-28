package com.cn.climax.wisdomparking.ui.pay.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class TestOnlineActivity extends BaseSwipeBackActivity {

    public static final String DEFAULT_TEST_URL = "http://www.baidu.com";

    @Override
    protected int initContentView() {
        return R.layout.activity_test_online;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        final WebView webView = (WebView) this.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

        });

        webView.loadUrl(DEFAULT_TEST_URL);
    }

}
