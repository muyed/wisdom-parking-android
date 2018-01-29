package cn.hs.com.wovencloud.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.app.framework.utils.SharedUtil;
import com.app.framework.widget.alertview.AlertView;
import com.app.framework.widget.alertview.OnItemClickListener;

import java.lang.ref.WeakReference;

import cn.hs.com.wovencloud.Core;
import cn.hs.com.wovencloud.base.global.Constant;
import cn.hs.com.wovencloud.data.remote.response.IMLookDetailPriceResponse;
import cn.hs.com.wovencloud.data.remote.response.IMModifyPriceResponse;
import cn.hs.com.wovencloud.ui.common.account.LoginActivity;
import cn.hs.com.wovencloud.ui.purchaser.setting.activity.IdentityAuthenticationActivity;
import cn.hs.com.wovencloud.widget.dialog.CommomDialog;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;


/**
 * author：leo on 2016/6/2 18:54
 * email： leocheung4ever@gmail.com
 * description: Toast
 * what & why is modified:
 */
public class TT {

    public static void showShortError(String msg) {
        Toasty.error(ContextHolderUtil.getContext(), msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void showShortSuccess(String msg) {
        Toasty.success(ContextHolderUtil.getContext(), msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void showShortInfo(String msg) {
        Toasty.info(ContextHolderUtil.getContext(), msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void showShortWarn(String msg) {
        Toasty.warning(ContextHolderUtil.getContext(), msg, Toast.LENGTH_SHORT, true).show();
    }

    public static void showShortUsual(String msg) {
        Toasty.normal(ContextHolderUtil.getContext(), msg).show();
    }

    public static void showShortUsualWithIcon(String msg, String iconPath) {
        Toasty.normal(ContextHolderUtil.getContext(), msg, Drawable.createFromPath(iconPath)).show();
    }

    public static void showShortCustom(String msg, String iconPath, int colorTint, boolean withIcon, boolean shouldTint) {
        Toasty.custom(ContextHolderUtil.getContext(), msg, Drawable.createFromPath(iconPath), ContextCompat.getColor(ContextHolderUtil.getContext(), colorTint), Toast.LENGTH_SHORT, withIcon,
                shouldTint).show();
    }

    public static void showPhoneDialog(final WeakReference<Activity> context) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context.get(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("拨打热线")
                .setContentText("0579-85841555\n" +
                        "\n是否拨打？")
                .setCancelText("　取　消　")
                .setConfirmText("　拨　打　")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "057985841555"));
                        context.get().startActivity(intent);
                    }
                })
                .show();
    }

    public static void showCommonDialog(final WeakReference<Activity> context, String title, String content, String cancelTitle, String confirmTitle, final int showType, SweetAlertDialog.OnSweetClickListener onConfirmListener) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context.get(), showType);
        sweetAlertDialog.setTitleText(title)
                .setContentText(content)
                .setCancelText(cancelTitle)
                .setConfirmText(confirmTitle)
                .setConfirmClickListener(onConfirmListener)
                .show();
    }

    public static void showCommonDialog(final String title, String content, final CommomDialog.OnCloseListener onCloseListener) {
        showCommonDialog(title, content, "　取消　", "　确定　", onCloseListener);
    }

    public static void showCommonDialog(final String title, String content, String cancelTitle, String confirmTitle, final CommomDialog.OnCloseListener onCloseListener) {
        final WeakReference<Activity> activity = new WeakReference<>(Core.getInstances().getCurrentActivity());
        if (activity.get() != null) {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity.get(), SweetAlertDialog.NORMAL_TYPE);
            sweetAlertDialog.setTitleText(title)
                    .setContentText(content)
                    .setCancelText(cancelTitle)
                    .setConfirmText(confirmTitle)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            onCloseListener.onClick(sweetAlertDialog, false);
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            onCloseListener.onClick(sweetAlertDialog, true);
                            sweetAlertDialog.dismiss();
                        }
                    });
            if (!activity.get().isFinishing()) {
                sweetAlertDialog.show();
            }
        }
    }


    public static void showCertificateDialog(final WeakReference<Activity> context) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context.get(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("温馨提示")
                .setContentText("您目前身份尚未被认证\n" +
                        "\n请先进行身份认证")
                .setCancelText("　取　消　")
                .setConfirmText("前去认证")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        context.get().startActivityForResult(new Intent(context.get(), IdentityAuthenticationActivity.class), Constant.REQUEST_PERSONAL_AUTH_CODE);
                    }
                })
                .show();
    }

    public static void showTouristsModelDialog(final WeakReference<Activity> context) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context.get(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("温馨提示")
                .setContentText("您目前处于游客模式\n" +
                        "\n请先进行登录")
                .setCancelText("　取　消　")
                .setConfirmText("　去登录　")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        context.get().startActivity(new Intent(context.get(), LoginActivity.class));
                        context.get().finish();
                    }
                })
                .show();
    }

    public static void showIosDialog(final WeakReference<Activity> context, String title, String content, String confirmTitle, String cancelTitle, OnItemClickListener onItemClickListener) {
        new AlertView(title, content, cancelTitle, new String[]{confirmTitle}, null, context.get(),
                AlertView.Style.Alert, onItemClickListener).show();
    }

    public static void showPriceDialog(WeakReference<Activity> weakReference, IMModifyPriceResponse.InquiryGoodsInfoBean.StdGoodsInfoBean mStdGoodsInfo) {
        AlertView alertView = new AlertView("售价: " + mStdGoodsInfo.getPrice_new() + "  " + mStdGoodsInfo.getPrice_time(), "成交价: " + mStdGoodsInfo.getOrder_price() + "  " + mStdGoodsInfo.getOrder_time(),
                null, null, null, weakReference.get(),
                AlertView.Style.Alert, null);
        alertView.show();
        alertView.setCancelable(true);
    }

    public static void showPriceCaiDialog(WeakReference<Activity> weakReference, IMLookDetailPriceResponse.QuoteGoodsInfoBean.StdGoodsInfoBean mStdGoodsInfo) {
        AlertView alertView = new AlertView("售价: " + mStdGoodsInfo.getPrice_new() + "  " + mStdGoodsInfo.getPrice_time(), "成交价: " + mStdGoodsInfo.getOrder_price() + "  " + mStdGoodsInfo.getOrder_time(),
                null, null, null, weakReference.get(),
                AlertView.Style.Alert, null);
        alertView.show();
        alertView.setCancelable(true);
    }

    public static void showBottomDialog(final WeakReference<Activity> context, OnItemClickListener listener) {
        new AlertView.Builder().setContext(context.get())
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("选择")
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive("创建公司")
                .setOthers(null)
                .setOnItemClickListener(listener)
                .build()
                .show();
    }

    public static void showSwitchCompanyDialog(final WeakReference<Activity> context, OnItemClickListener listener, String message) {
        AlertView.Builder alertBuilder = new AlertView.Builder().setContext(context.get())
                .setStyle(AlertView.Style.Alert)
                .setTitle("是否切换公司?")
                .setMessage(message)
                .setCancelText("取消")
                .setDestructive("切换")
                .setOthers(null)
                .setOnItemClickListener(listener);
        AlertView alertView = new AlertView(alertBuilder);
        alertView.setClosable(false, keylistener);
        alertView.setCancelable(false);
        alertView.show();
    }

    public static void showSwitchRoleDialog(final WeakReference<Activity> context, OnItemClickListener listener, int role) {
        AlertView.Builder alertBuilder = new AlertView.Builder().setContext(context.get())
                .setStyle(AlertView.Style.Alert)
                .setTitle("温馨提示")
                .setMessage(role == 1 ? "是否切换为采购商？" : "是否切换为供应商？")
                .setCancelText("取消")
                .setDestructive("切换")
                .setOthers(null)
                .setOnItemClickListener(listener);
        AlertView alertView = new AlertView(alertBuilder);
        alertView.setClosable(false, keylistener);
        alertView.setCancelable(false);
        alertView.show();
    }

    public static void showReLoginDialog(final WeakReference<Activity> context, OnItemClickListener listener) {
        SharedUtil.getInstance(Core.getInstances()).put("is_show_dialog", true);
        AlertView.Builder alertBuilder = new AlertView.Builder().setContext(context.get())
                .setStyle(AlertView.Style.Alert)
                .setTitle("温馨提示")
                .setMessage("您的帐号已在其他设备登录")
                .setCancelText(null)
                .setDestructive("确定")
                .setOthers(null)
                .setOnItemClickListener(listener);
        AlertView alertView = new AlertView(alertBuilder);
        alertView.setClosable(false, keylistener);
        alertView.setCancelable(false);
        alertView.show();
    }

    public static void showRefreshLoginDialog(final WeakReference<Activity> context, OnItemClickListener listener) {
        SharedUtil.getInstance(Core.getInstances()).put("is_show_account_expired", true);
        AlertView.Builder alertBuilder = new AlertView.Builder().setContext(context.get())
                .setStyle(AlertView.Style.Alert)
                .setTitle("温馨提示")
                .setMessage("您当前帐号已过期，请重新登录")
                .setCancelText(null)
                .setDestructive("重新登录")
                .setOthers(null)
                .setOnItemClickListener(listener);
        AlertView alertView = new AlertView(alertBuilder);
        alertView.setClosable(false, keylistener);
        alertView.setCancelable(false);
        alertView.show();
    }

    private static View.OnKeyListener keylistener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent event) {
            return keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;
        }
    };

}
