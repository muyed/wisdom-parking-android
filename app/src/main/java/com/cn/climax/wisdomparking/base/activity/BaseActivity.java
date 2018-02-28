package cn.hs.com.wovencloud.base.me.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.aliyun.clientinforeport.util.AppUtils;
import com.app.framework.app.AppActivityManager;
import com.app.framework.utils.SharedUtil;
import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.List;

import butterknife.ButterKnife;
import cn.hs.com.wovencloud.Core;
import cn.hs.com.wovencloud.base.global.IGlobalLoginStateListener;
import cn.hs.com.wovencloud.base.me.NetBroadCastReceiver;
import cn.hs.com.wovencloud.data.apiUtils.ApiParamsKey;
import cn.hs.com.wovencloud.data.apiUtils.ApiUtils;
import cn.hs.com.wovencloud.data.apiUtils.WrapJsonBeanCallback;
import cn.hs.com.wovencloud.ui.purchaser.setting.response.UserInfoBean;
import okhttp3.Call;
import okhttp3.Response;
import wanjian.renderingperformance.RenderingPerformance;

/**
 * author：leo on 2017/2/8 11:02
 * email： leocheung4ever@gmail.com
 * description: app所有Activity基类
 * what & why is modified:
 */

public abstract class BaseActivity extends RootBaseActivity implements IGlobalLoginStateListener, NetBroadCastReceiver.NetEvent, ActivityCompat.OnRequestPermissionsResultCallback {

    public static NetBroadCastReceiver.NetEvent event;
    private final String mPageName = "Analytics_JZY";
    private int netMobile;
    private HomeWatcherReceiver mHomeWatcherReceiver;
    private boolean isNeedFinish = false;

    @Override
    public void LogOut(Context context) {
    }

    @Override
    public void OffLine() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    private void isNetConnect() {
    }

    /**
     * 当前上下文
     */
    protected Context mContext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerAppHomeReceiver();
        ButterKnife.bind(this);
        mContext = this;
        event = this;
        AppActivityManager.getAppManager().addActivity(this);
        /** 友盟统计 */
        UMConfigure.setLogEnabled(false);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_DUM_NORMAL);

        if (!isNeedReturnRefresh())
            initUiAndListener(savedInstanceState);
    }

    public Context getContext() {
        return mContext;
    }

    public Activity getActivity() {
        return this;
    }

    /**
     * 是否需要提示抢登提示框 默认需要 , 但是首页之外的界面不需要
     */
    protected boolean isNeedToastReLogin() {
        return true;
    }

    private static final String TAG = "BaseActivity";

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(mContext);

//        RenderingPerformance.resume(this);

        if (isNeedReturnRefresh())
            initUiAndListener(null);
        if (isNeedGotUserInfo()) {
            String authStatus = SharedUtil.getInstance(getActivity()).get(ApiParamsKey.AUTH_STATUS);
            if (TextUtils.isEmpty(authStatus) || !authStatus.equals("1")) {
                Log.i(TAG, "onResume: " + authStatus);
                ApiUtils.getInstance().getUserInfo(new WrapJsonBeanCallback<UserInfoBean>(getActivity()) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                    }

                    @Override
                    protected void onExecuteSuccess(UserInfoBean bean, Call call) {
                        if (!TextUtils.isEmpty(bean.getSeller_id())) {
                            SharedUtil.getInstance(getActivity()).put(ApiParamsKey.AUTH_STATUS, "1");
                            SharedUtil.getInstance(getActivity()).put(ApiParamsKey.SELLER_ID, bean.getSeller_id());
                            SharedUtil.getInstance(getActivity()).put(ApiParamsKey.LOGIN_SELLER_ID, bean.getSeller_id());
                        } else {
                            SharedUtil.getInstance(getActivity()).put(ApiParamsKey.AUTH_STATUS, "-1");
                        }
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {
                    }

                    @Override
                    protected boolean setDialogShow() {
                        return false;
                    }
                });
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(mContext);

//        RenderingPerformance.pause(this);
    }

    /**
     * 是否需要获取用户信息 默认每个继承此基类的Activity均需要  如不需要 请返回false, 避免参数错误
     */
    protected boolean isNeedGotUserInfo() {
        return true;
    }

    /**
     * 是否需要返回前一个界面刷新 默认不刷新
     */
    protected boolean isNeedReturnRefresh() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**  取消当前页面的请求 */
        OkGo.getInstance().cancelTag(this);
        if (mHomeWatcherReceiver != null) {
            try {
                unregisterReceiver(mHomeWatcherReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onKeyDownBack(int keyCode, KeyEvent event) {
        if (Core.getInstances().getIObserveKeyListener() != null) {
            Core.getInstances().getIObserveKeyListener().resetOnKeyDown();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK: {// 返回键 切换至桌面
                onKeyDownBack(keyCode, event);
                return true;
            }
            default:
                return false;
        }
    }

    /**
     * 监听Home键广播器
     */
    private void registerAppHomeReceiver() {
        mHomeWatcherReceiver = new HomeWatcherReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeWatcherReceiver, filter);
    }

    private static final int NUM_BUTTONS = 8; //手机点击最近app键最多可显示的app数  系统一般默认为是8个
    private static final int MAX_RECENT_TASKS = NUM_BUTTONS * 2;    // allow for some discards
//    private static final boolean DBG_FORCE_EMPTY_LIST = false;

    public class HomeWatcherReceiver extends BroadcastReceiver {

        private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
        private static final String SYSTEM_DIALOG_REASON_RECENT_KEY = "recentapps";
        private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            Log.i("HomeWatcherReceiver ", "intentAction =" + intentAction);
            if (TextUtils.equals(intentAction, Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                Log.i("HomeWatcherReceiver ", "reason =" + reason);
                if (TextUtils.equals(SYSTEM_DIALOG_REASON_HOME_KEY, reason)) { //todo  可能会处理双击Home键情况 TBD
                    Log.i("HomeWatcherReceiver ", "----退到后台");
//                    if (SharedUtil.getInstance(Core.getInstances()).get("is_show_dialog", false))
//                        SharedUtil.getInstance(context).put(ApiParamsKey.HOME_APP_KEY, true);
//                    else
//                        SharedUtil.getInstance(context).put(ApiParamsKey.HOME_APP_KEY, false);
                } else if (TextUtils.equals(SYSTEM_DIALOG_REASON_RECENT_KEY, reason)) {
                    final PackageManager pm = context.getPackageManager();
                    final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                    final List<ActivityManager.RecentTaskInfo> recentTasks = am.getRecentTasks(MAX_RECENT_TASKS, ActivityManager.RECENT_IGNORE_UNAVAILABLE);

                    ActivityInfo homeInfo = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).resolveActivityInfo(pm, 0);

                    int index = 0;
                    int numTasks = recentTasks.size();
                    for (int i = 0; i < numTasks && (index < NUM_BUTTONS); ++i) {
                        final ActivityManager.RecentTaskInfo info = recentTasks.get(i);
//                        if (DBG_FORCE_EMPTY_LIST && (i == 0)) continue;

                        Intent intentBase = new Intent(info.baseIntent);
                        if (info.origActivity != null) {
                            intentBase.setComponent(info.origActivity);
                        }
                        if (homeInfo != null) {
                            if (AppUtils.getPackageName(context).equals(intentBase.getComponent().getPackageName())) {
                                Log.i("HomeWatcherReceiver ", "homeInfo =" + intentBase.getComponent().getPackageName());
                                if (SharedUtil.getInstance(Core.getInstances()).get("is_show_dialog", false))
                                    SharedUtil.getInstance(context).put(ApiParamsKey.RECENT_APP_KEY, true);
                                else
                                    SharedUtil.getInstance(context).put(ApiParamsKey.RECENT_APP_KEY, false);
                            }
                        }
                    }
                }
            }
        }
    }
}
