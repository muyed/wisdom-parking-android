package cn.hs.com.wovencloud.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;

/**
 * author：leo on 2017/9/28 0028 13:37
 * email： leocheung4ever@gmail.com
 * description: 判断某个服务是否在后台运行
 * what & why is modified:
 */
public class ServiceUtils {

    /**
     * 判断服务是否开启
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }
}
