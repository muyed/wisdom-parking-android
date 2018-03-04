package com.cn.climax.i_carlib.platform.util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * JSON转MAP工具类
 */
public class Utils {

    public static Map<String, String> jsonToMap(JSONObject val) {
        HashMap map = new HashMap();

        Iterator iterator = val.keys();

        while (iterator.hasNext()) {
            String var4 = (String) iterator.next();
            map.put(var4, val.opt(var4) + "");
        }

        return map;
    }
}
