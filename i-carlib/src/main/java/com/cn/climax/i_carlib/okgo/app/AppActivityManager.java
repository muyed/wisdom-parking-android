package com.app.framework.app;

import android.app.Activity;

import com.app.framework.loger.Loger;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 *
 * @author
 * @date
 * @email
 */
public class AppActivityManager {
    private String TAG = "AppActivityManager:";

    private static Stack<Activity> activityStack;
    private static AppActivityManager instance;

    private AppActivityManager() {

    }

    /**
     * 栈 中是否有指定的Activity
     */
    public static boolean containsActivity(Activity activity) {
        return activityStack.contains(activity);
    }

    /**
     * 单实例 , UI无需考虑多线程同步问题
     */
    public static AppActivityManager getAppManager() {
        if (instance == null) {
            instance = new AppActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
        Loger.d(TAG + "ActivityList.onCreate(" + activity.getLocalClassName() + "),size() = " + activityStack.size() + ";");

    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 获取当前Activity的上一个activity
     */
    public Activity lastActivity() {
        if (activityStack == null || activityStack.isEmpty() || activityStack.size() <= 1) {
            return null;
        }
        return activityStack.elementAt(activityStack.size() - 2);
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
        Loger.d(TAG + "ActivityList.onDestroy栈顶(" + activity.getLocalClassName() + "),size() = " + activityStack.size() + ";");

    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            Loger.d(TAG + "ActivityList.onDestroy(" + activity.getLocalClassName() + "),size() = " + activityStack.size() + ";");
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     */
    public void finishOthersActivity(Class<?> cls) {

        Stack<Activity> delList = new Stack<>();// 用来装需要删除的元素

        for (Activity activity : activityStack) {
            if (!(activity.getClass().equals(cls))) {
                delList.add(activity);
            }
        }

        for (Activity activity : delList) {
            finishActivity(activity);
        }
        activityStack.removeAll(delList);// 遍历完成后执行删除
        delList.clear();
    }

    /**
     * 结束指定的Activity
     */
//    public void finishActivity(Class<?>... act) {
//        for (Activity activity : activityStack) {
//            for (Class clazz : act) {
//                if (activity.getClass().equals(clazz)) {
//                    finishActivity(activity);
//                }
//            }
//        }
//    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                Loger.d(TAG + "ActivityList.finish(" + activityStack.get(i).getLocalClassName() + ");");
                activityStack.get(i).finish();
                activityStack.get(i).overridePendingTransition(0, 0);
            }
        }
        if (BaseApplication.getInstance() != null) {
            // app 退出
        }
        activityStack.clear();
    }

}
