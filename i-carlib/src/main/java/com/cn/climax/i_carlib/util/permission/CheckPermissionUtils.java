package com.cn.climax.i_carlib.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * author：leo on 2017/9/23 0023 16:28
 * email： leocheung4ever@gmail.com
 * description: 检查权限工具类
 * what & why is modified:
 */
public class CheckPermissionUtils {

//    private static CheckPermissionUtils instance;

//    public static CheckPermissionUtils getInstance(){
//        if (instance == null){
//            instance = new CheckPermissionUtils();
//        }
//        return instance;
//    }


    private CheckPermissionUtils() {
    }

    //需要申请的权限
    private static String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };
    
    private static String[] locationPermission = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };


    //相机权限 相册权限
    public static String[] cameraPermissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};


    //相机权限 相册权限
    public static String[] readPermissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //检测权限
    public static String[] checkPermission(Context context) {
        List<String> data = new ArrayList<>();//存储未申请的权限
        for (String permission : permissions) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, permission);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {//未申请
                data.add(permission);
            }
        }
        return data.toArray(new String[data.size()]);
    }
    
    //检测定位权限
    public static String[] checkLocalePermission(Context context){
        List<String> data = new ArrayList<>();//存储未申请的权限
        for (String permission : locationPermission) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, permission);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {//未申请
                data.add(permission);
            }
        }
        return data.toArray(new String[data.size()]);
    }

    public static List<String> getCameraPermissionsList() {
        List list = new ArrayList();
        for (String permission : cameraPermissions) {
            list.add(permission);
        }
        return list;
    }


    //检测相机 相册 权限
    public static String[] checkCameraPermission(Context context) {
        List<String> data = new ArrayList<>();//存储未申请的权限
        for (String permission : cameraPermissions) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, permission);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {//未申请
                data.add(permission);
            }
        }
        return data.toArray(new String[data.size()]);
    }



    //检测相机 相册 权限
    public static String[] checkReadPermission(Context context) {
        List<String> data = new ArrayList<>();//存储未申请的权限
        for (String permission : readPermissions) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, permission);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {//未申请
                data.add(permission);
            }
        }
        return data.toArray(new String[data.size()]);
    }

}
