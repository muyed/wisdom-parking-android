package com.cn.climax.i_carlib.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;


import com.cn.climax.i_carlib.uiframework.sweetalert.SweetAlertDialog;

import java.lang.ref.WeakReference;

/**
 * author：leo on 2016/6/2 18:54
 * email： leocheung4ever@gmail.com
 * description: Toast
 * what & why is modified:
 */
public class TT {

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
//                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                            // TODO: Consider calling
//                            //    ActivityCompat#requestPermissions
//                            // here to request the missing permissions, and then overriding
//                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                            //                                          int[] grantResults)
//                            // to handle the case where the user grants the permission. See the documentation
//                            // for ActivityCompat#requestPermissions for more details.
//                            return;
//                        }
//                        context.get().startActivity(intent);
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

//    public static void showCommonDialog(final String title, String content, final CommomDialog.OnCloseListener onCloseListener) {
//        showCommonDialog(title, content, "　取消　", "　确定　", onCloseListener);
//    }

//    public static void showCommonDialog(final String title, String content, String cancelTitle, String confirmTitle, final CommomDialog.OnCloseListener onCloseListener) {
//        final WeakReference<Activity> activity = new WeakReference<>(Core.getInstances().getCurrentActivity());
//        if (activity.get() != null) {
//            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity.get(), SweetAlertDialog.NORMAL_TYPE);
//            sweetAlertDialog.setTitleText(title)
//                    .setContentText(content)
//                    .setCancelText(cancelTitle)
//                    .setConfirmText(confirmTitle)
//                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            onCloseListener.onClick(sweetAlertDialog, false);
//                            sweetAlertDialog.dismiss();
//                        }
//                    })
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            onCloseListener.onClick(sweetAlertDialog, true);
//                            sweetAlertDialog.dismiss();
//                        }
//                    });
//            if (!activity.get().isFinishing()) {
//                sweetAlertDialog.show();
//            }
//        }
//    }


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
                    }
                })
                .show();
    }

    private static View.OnKeyListener keylistener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent event) {
            return keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;
        }
    };

}
