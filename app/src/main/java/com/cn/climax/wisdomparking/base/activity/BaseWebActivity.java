package cn.hs.com.wovencloud.base.me.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;

import butterknife.BindView;
import cn.hs.com.wovencloud.R;
import cn.hs.com.wovencloud.base.me.BaseSwipeBackActivity;
import cn.hs.com.wovencloud.widget.webview.WebLayout;

/**
 * author：leo on 2017/12/12 0012 22:19
 * email： leocheung4ever@gmail.com
 * description: 基类web view activity
 * what & why is modified:
 */
public class BaseWebActivity extends BaseSwipeBackActivity {

    protected AgentWeb mAgentWeb;

    @BindView(R.id.container)
    LinearLayout mLinearLayout;

    @Override
    protected int initContentView() {
        return R.layout.activity_web;
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
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, setToolbarTitle());
    }

    /**
     * 设置中间标题
     */
    protected String setToolbarTitle() {
        return "";
    }

    @Override
    protected boolean isNeedGotUserInfo() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long p = System.currentTimeMillis();
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(getParentViewGroup(), new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
                .setReceivedTitleCallback(mCallback)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecutityType(AgentWeb.SecurityType.strict)
                .setWebLayout(new WebLayout(this))
//                .openParallelDownload()//打开并行下载 , 默认串行下载
//                .setNotifyIcon(R.mipmap.download) //下载图标
//                .setOpenOtherAppWays(DefaultWebClient.OpenOtherAppWays.ASK)//打开其他应用时，弹窗质询用户前往其他应用
//                .interceptUnkownScheme() //拦截找不到相关页面的Scheme
                .createAgentWeb()//
                .ready()
                .go(getUrl());

        long n = System.currentTimeMillis();
        Log.i("Info", "init used time:" + (n - p));
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

    protected ViewGroup getParentViewGroup() {
        return mLinearLayout;
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.i("Info", "progress:" + newProgress);
        }
    };

    public String getUrl() {
        return "https://p.jzyb2b.com/manager/public/missing";
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            Log.i("Info", "title:" + title);
            setToolBar(true, title);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("Info", "result:" + requestCode + " result:" + resultCode);
        mAgentWeb.uploadFileResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
