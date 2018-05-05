package com.cn.climax.wisdomparking.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.autonavi.ae.route.model.GeoPoint;

/**
 * author：leo on 2017/9/28 0028 10:11
 * email： leocheung4ever@gmail.com
 * description: 经纬度工具类
 * what & why is modified:
 */
public class LocationUtils {

    private GeocodeSearch geocoderSearch;

    // 纬度
    public static double latitude = 0.0;
    // 经度
    public static double longitude = 0.0;

    /**
     * 初始化位置信息
     */
//    public static void initLocation(Context context) {
//        LocationManager locationManager = (LocationManager) context
//                .getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            Location location = locationManager
//                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (location != null) {
//                latitude = location.getLatitude();
//                longitude = location.getLongitude();
//            }
//        } else {
//            LocationListener locationListener = new LocationListener() {
//
//                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
//                @Override
//                public void onStatusChanged(String provider, int status,
//                                            Bundle extras) {
//
//                }
//
//                // Provider被enable时触发此函数，比如GPS被打开
//                @Override
//                public void onProviderEnabled(String provider) {
//
//                }
//
//                // Provider被disable时触发此函数，比如GPS被关闭
//                @Override
//                public void onProviderDisabled(String provider) {
//
//                }
//
//                // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
//                @Override
//                public void onLocationChanged(Location location) {
//                    if (location != null) {
//
//                    }
//                }
//            };
//            locationManager
//                    .requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 0, locationListener);
//            Location location = locationManager
//                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if (location != null) {
//                latitude = location.getLatitude(); // 经度
//                longitude = location.getLongitude(); // 纬度
//            }
//        }
//    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系  
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求  
    }

}
