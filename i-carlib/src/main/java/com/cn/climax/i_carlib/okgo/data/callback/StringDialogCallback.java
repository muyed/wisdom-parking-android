package com.cn.climax.i_carlib.okgo.data.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.view.Window;

import com.cn.climax.i_carlib.uiframework.ioser.PromptDialog;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;


public abstract class StringDialogCallback extends StringCallback {

    //    private PromptDialog promptDialog;
    private ProgressDialog dialog;

    public StringDialogCallback(Activity activity) {
        if (!activity.isFinishing()) {
            if (isShowDialog()) {
                //创建对象
//                promptDialog = new PromptDialog(activity);
                //设置自定义属性
//                promptDialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(3000);
                dialog = new ProgressDialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("加载中，请稍候...");
            }
        }
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        if (isShowDialog()) {
//            promptDialog.showLoading("加载中，请稍候...");
            //网络请求前显示对话框
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    private boolean isShowDialog() {
        return setDialogShow();
    }
    
    protected boolean setDialogShow() {
        return true;
    }

    @Override
    public void onAfter(@Nullable String s, @Nullable Exception e) {
        super.onAfter(s, e);
        if (isShowDialog()) {
//            promptDialog.dismissImmediately();
            //网络请求结束后关闭对话框
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
