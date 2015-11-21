package com.ytying.ytblog.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by UKfire on 15/11/21.
 */
public class JsonUtil {

    public static JSONObject string2JsonObject(String s){
        JSONObject jobj = JSON.parseObject(s);
        if(jobj == null)
            jobj = new JSONObject();
        return jobj;
    }

    public static JSONArray string2Array(String s){
        JSONArray jobj = JSON.parseArray(s);
        if(jobj == null)
            jobj = new JSONArray();
        return jobj;
    }

    public static <T>T Json2T(String s,Class<T> classOfT, T def){
        if(null == s)
            return def;
        try {
            return JSON.parseObject(s,classOfT);
        }catch (Exception e){
            e.printStackTrace();
            return def;
        }
    }

    public static <T>List<T> string2List(String s,Class<T> classOfT){
        if(null == s)
            return new ArrayList<>();
        try {
            List list = JSON.parseArray(s,classOfT);
            if(null == list)
                list = new ArrayList();
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static String Object2Json(Object o) {
        try {
            String s = JSON.toJSONString(o);
            if (s == null)
                s = new String();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return new String();
        }
    }

    public static String list2JsonSerial(List l){
        if(null == l)
            return new String();
        try {
            String s = JSON.toJSONString(l, SerializerFeature.WriteClassName);
            if(null == s)
                s = new String();
            return s;
        }catch (Exception e){
            e.printStackTrace();
            return new String();
        }
    }

    public static JSONArray Collection2JsonArray(Collection collection){
        try {
            JSONArray jsonArray = (JSONArray) JSON.toJSON(collection);
            if(jsonArray == null)
                jsonArray = new JSONArray();
            return jsonArray;
        }catch (Exception e){
            e.printStackTrace();
            return new JSONArray();
        }
    }

}
