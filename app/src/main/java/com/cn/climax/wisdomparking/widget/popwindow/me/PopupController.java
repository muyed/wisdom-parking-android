package com.cn.climax.wisdomparking.widget.popwindow.me;

/**
 * author：leo on 2017/9/28 0028 16:55
 * email： leocheung4ever@gmail.com
 * description: popup 控制器接口
 * what & why is modified:
 */
public interface PopupController {
    /**
     * 消失前是否进行动作
     */
    boolean onBeforeDismiss();

    /**
     * 是否立即回调消失
     */
    boolean callDismissAtOnce();
}
