package com.cn.climax.wisdomparking.util;

import android.hardware.Camera;
import android.support.v7.widget.AppCompatImageView;

import com.cn.climax.wisdomparking.R;

/**
 * author：leo on 2018/5/5 0005 16:17
 * email： leocheung4ever@gmail.com
 * description: 手电筒功能
 * what & why is modified:
 */
public class FlashUtils {

    private static FlashUtils utils;
    private static Camera camera;
    public static boolean mIsOpen = true;

    //使用单例模式在这里初始化相机
    public static FlashUtils getInstance() {
        if (utils == null) {
            utils = new FlashUtils();
        }
        try {
            if (camera == null) {
                camera = Camera.open();
            }
        } catch (Exception e) {
            if (camera != null) {
                camera.release();
            }
            camera = null;
        }
        return utils;
    }

    //参考二维码工具的闪光灯
    public void switchFlash(AppCompatImageView flashLightIv) {
        try {
            Camera.Parameters parameters = camera.getParameters();
            if (mIsOpen) {
                if (parameters.getFlashMode().equals("torch")) {
                    return;
                } else {
                    flashLightIv.setImageResource(R.drawable.ic_open_scan);
                    parameters.setFlashMode("torch");
                }
            } else {
                if (parameters.getFlashMode().equals("off")) {
                    return;
                } else {
                    flashLightIv.setImageResource(R.drawable.ic_close_light);
                    parameters.setFlashMode("off");
                }
            }
            camera.setParameters(parameters);
        } catch (Exception e) {
            finishFlashUtils();
        }
        mIsOpen = !mIsOpen;
    }

    //页面销毁的时候调用此方法
    public void finishFlashUtils() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
        camera = null;
    }
}
