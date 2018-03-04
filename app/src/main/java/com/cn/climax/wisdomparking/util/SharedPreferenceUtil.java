package com.cn.climax.wisdomparking.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * author: leo on 2016/6/24 0024/13:01
 * email : leocheung4ever@gmail.com
 * description:
 * what & why is modified:
 */
public class SharedPreferenceUtil {
    private Context mContext;
    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor edit = null;

    /**
     * 创建 DefaultSharedPreferences
     */
    public SharedPreferenceUtil(Context context) {
        this(context, PreferenceManager.getDefaultSharedPreferences(context));
    }

    /**
     * 通过文件名 创建 SharedPreferences
     */
    public SharedPreferenceUtil(Context context, String filename) {
        this(context, context.getSharedPreferences(filename,
                Context.MODE_WORLD_WRITEABLE));
    }

    /**
     * 通过SharedPreferences创建 SharedPreferences
     */
    public SharedPreferenceUtil(Context context,
                                SharedPreferences sharedPreferences) {
        this.mContext = context;
        this.mSharedPreferences = sharedPreferences;
        edit = mSharedPreferences.edit();
    }

    public void setString(String key, String value) {

        edit.putString(key, value);
        edit.commit();
    }

    public void setInt(String key, int value) {

        edit.putInt(key, value);
        edit.commit();
    }

    public void setBoolean(String key, Boolean value) {

        edit.putBoolean(key, value);
        edit.commit();
    }

    public void setByte(String key, byte[] value) {

        setString(key, new String(value));
    }

    public void setShort(String key, short value) {

        setString(key, String.valueOf(value));
    }

    public void setLong(String key, long value) {

        edit.putLong(key, value);
        edit.commit();
    }

    public void setFloat(String key, float value) {

        edit.putFloat(key, value);
        edit.commit();
    }

    public void setDouble(String key, double value) {

        setString(key, String.valueOf(value));
    }

    public void setString(int resID, String value) {

        setString(this.mContext.getString(resID), value);

    }

    public void setInt(int resID, int value) {

        setInt(this.mContext.getString(resID), value);
    }

    public void setBoolean(int resID, Boolean value) {

        setBoolean(this.mContext.getString(resID), value);
    }

    public void setByte(int resID, byte[] value) {

        setByte(this.mContext.getString(resID), value);
    }

    public void setShort(int resID, short value) {

        setShort(this.mContext.getString(resID), value);
    }

    public void setLong(int resID, long value) {

        setLong(this.mContext.getString(resID), value);
    }

    public void setFloat(int resID, float value) {

        setFloat(this.mContext.getString(resID), value);
    }

    public void setDouble(int resID, double value) {

        setDouble(this.mContext.getString(resID), value);
    }

    public String getString(String key, String defaultValue) {

        return mSharedPreferences.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {

        return mSharedPreferences.getInt(key, defaultValue);
    }

    public boolean getBoolean(String key, Boolean defaultValue) {

        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public byte[] getByte(String key, byte[] defaultValue) {

        try {
            return getString(key, "").getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public short getShort(String key, Short defaultValue) {

        try {
            return Short.valueOf(getString(key, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public long getLong(String key, Long defaultValue) {

        return mSharedPreferences.getLong(key, defaultValue);
    }

    public float getFloat(String key, Float defaultValue) {

        return mSharedPreferences.getFloat(key, defaultValue);
    }

    public double getDouble(String key, Double defaultValue) {

        try {
            return Double.valueOf(getString(key, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public String getString(int resID, String defaultValue) {

        return getString(this.mContext.getString(resID), defaultValue);
    }

    public int getInt(int resID, int defaultValue) {

        return getInt(this.mContext.getString(resID), defaultValue);
    }

    public boolean getBoolean(int resID, Boolean defaultValue) {

        return getBoolean(this.mContext.getString(resID), defaultValue);
    }

    public byte[] getByte(int resID, byte[] defaultValue) {

        return getByte(this.mContext.getString(resID), defaultValue);
    }

    public short getShort(int resID, Short defaultValue) {

        return getShort(this.mContext.getString(resID), defaultValue);
    }

    public long getLong(int resID, Long defaultValue) {

        return getLong(this.mContext.getString(resID), defaultValue);
    }

    public float getFloat(int resID, Float defaultValue) {

        return getFloat(this.mContext.getString(resID), defaultValue);
    }

    public double getDouble(int resID, Double defaultValue) {

        return getDouble(this.mContext.getString(resID), defaultValue);
    }

    public void remove(String key) {

        edit.remove(key);
        edit.commit();
    }

    public void remove(String... keys) {

        for (String key : keys)
            remove(key);
    }

    public void clear() {

        edit.clear();
        edit.commit();
    }

    public static SharedPreferenceUtil getInstance(Context context) {
        return new SharedPreferenceUtil(context);
    }
}
