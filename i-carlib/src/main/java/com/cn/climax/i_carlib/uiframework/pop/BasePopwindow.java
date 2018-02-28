package com.app.framework.widget.popwindow;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.app.framework.R;
import com.app.framework.app.BaseApplication;

/**
 * author:xiongx 2017/9/14.
 */

public abstract class BasePopwindow extends PopupWindow {

    public View view;

    public void init() {
        View view = LayoutInflater.from(BaseApplication.getInstance().getCurrentActivity()).inflate(initLayoutId(), null);
        setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);

        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        darkenBackground(0.3f);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
        this.view=view;
        initView(view);
    }

    public abstract int initLayoutId();

    public abstract void initView(View view);

    public void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = BaseApplication.getInstance().getCurrentActivity().getWindow().getAttributes();
        lp.alpha = bgcolor;

        BaseApplication.getInstance().getCurrentActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        BaseApplication.getInstance().getCurrentActivity().getWindow().setAttributes(lp);
    }

    /**
     * 底部弹出动画
     */
    public BasePopwindow bottomPopup() {
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwin_anim_style);
        return this;
    }

    public void showBottom(View view) {
        //底部显示Pop
        this.showAtLocation(view, Gravity.BOTTOM, 0, 10);
    }

    public void showBottom(View view, int bottom) {
        //底部显示Pop
        this.showAtLocation(view, Gravity.BOTTOM, 0, bottom);
    }

    /*显示在中间*/
    public void showCenter(View view) {
        //显示在中间
        this.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
    /*右侧显示*/
    public void showRight(View view){
        setAnimationStyle(R.style.AnimationRightFade);
        showAtLocation(view,Gravity.RIGHT,0,0);
    }
}
