package com.cn.climax.i_carlib.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.cn.climax.i_carlib.okgo.http.GsonConvert;

import java.util.List;
import java.util.Map;

public class SharedUtil {
    private static final String SHARED_NAME = "JZY_HLL_SHARED";
    private static final int SHARED_MODE = Activity.MODE_PRIVATE;
    private static SharedPreferences shared;
    private static SharedUtil instance = null;

    public static SharedUtil getInstance(Context context) {
        if (null == context) new IllegalArgumentException("context can not be null.");
        if (null == instance) {
            instance = new SharedUtil();
            shared = context.getSharedPreferences(SHARED_NAME, SHARED_MODE);
        }
        return instance;
    }


    public void clear() {
        shared.edit().clear().commit();
    }

    public boolean delete(String key) {
        SharedPreferences.Editor editor = shared.edit();
        return editor.remove(key).commit();
    }


    public boolean put(String key, String value) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public boolean put(String key, boolean value) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean put(String key, int value) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public boolean put(String key, float value) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public boolean put(String key, long value) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }


    public Map<String, ?> getAll() {
        return shared.getAll();
    }

    public int get(String key, int defValue) {
        return shared.getInt(key, defValue);
    }

    public String get(String key) {
        return shared.getString(key, "");
    }
    public String get(String key,String defValue) {
        return shared.getString(key, defValue);
    }
    public boolean get(String key, boolean defValue) {
        return shared.getBoolean(key, defValue);
    }

    public float get(String key, float defValue) {
        return shared.getFloat(key, defValue);
    }

    public long get(String key, long defValue) {
        return shared.getLong(key, defValue);
    }


    public <T> List<T> getArray(String key, Class<T> clazz) {
        String json = shared.getString(key, null);
        if (null == json) return null;
//        return JSON.parseArray(json, clazz);
        return (List<T>) GsonConvert.fromJson(json, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        String json = shared.getString(key, null);
        if (null == json) return null;
//        return JSON.parseObject(json, clazz);
        return GsonConvert.fromJson(json, clazz);
    }

}
