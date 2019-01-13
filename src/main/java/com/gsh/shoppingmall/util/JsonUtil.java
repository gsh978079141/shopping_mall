package com.gsh.shoppingmall.util;

import com.google.gson.Gson;

import java.util.Map;

/**
 * @author gsh
 * @Title: JsonUtil json处理工具类
 * @Package com.gsh.shoppingmall.util
 * @Description:
 * @date 2018/7/17 14:58
 */
public class JsonUtil {
    private static Gson gson = new Gson();

    /**
     * map 转换成json
     *
     * @param map
     * @return
     */
    public static String jsonToString(Map map) {
        return gson.toJson(map);
    }
}
