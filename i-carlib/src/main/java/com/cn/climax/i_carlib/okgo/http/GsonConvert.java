package com.app.framework.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GsonConvert {

    private static Gson create() {
        return GsonConvert.GsonHolder.gson;
    }

    private static class GsonHolder {
        private static Gson gson = new GsonBuilder().serializeNulls().create();
    }

    public static <T> T fromJson(String json, Class<T> type) throws JsonIOException, JsonSyntaxException {
            T t = null;
        try {
           t= create().fromJson(json, type);
        }catch (Exception e){
            return t;
        }
        return t;
    }

    public static <T> T fromJson(String json, Type type) {
        return create().fromJson(json, type);
    }

    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(reader, typeOfT);
    }

    public static <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        return create().fromJson(json, classOfT);
    }

    public static <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(json, typeOfT);
    }

    public static String toJSONString(Object src) {
        return create().toJson(src);
    }

    public static String toJSONString(Object src, Type typeOfSrc) {
        return create().toJson(src, typeOfSrc);
    }
}