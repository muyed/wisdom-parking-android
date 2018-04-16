package com.app.framework.widget.xrecyclerview;

/**
 * author：leo on 2017/9/17 0017 22:58
 * email： leocheung4ever@gmail.com
 * description: 头部刷新 状态抽象接口
 * what & why is modified:
 */
public interface BaseRefreshHeader {

    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMove(float delta);

    boolean releaseAction();

    void refreshComplete();
}
