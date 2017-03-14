package com.example.testandroid2.tools;

import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类描述：字符串工具类
 * 创建人：G.G.Z
 * 创建时间：16/5/25 14:37
 */
public class StringUtils {


    /***
     * 将url与请求数据拼接为字符串
     *
     * @param url
     * @param map
     * @return
     */
    public static String getQueryUrl(String url, Map<String, String> map) {
        final StringBuilder result = new StringBuilder();
        if(map==null){
            return url;
        }
        for (ConcurrentHashMap.Entry<String, String> parameter : map.entrySet()) {
            try {
                final String encodedName = URLEncoder.encode(parameter.getKey(), "utf-8");
                final String encodedValue = URLEncoder.encode(parameter.getValue(), "utf-8");
                if (result.length() > 0) {
                    result.append("&");
                }
                result.append(encodedName);
                if (encodedValue != null) {
                    result.append("=");
                    result.append(encodedValue);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(TextUtils.isEmpty(result.toString())){
            return url;
        }
        return url + "?" + result.toString();
    }
}
