package com.ytying.ytblog.model;

import com.alibaba.fastjson.JSONObject;
import com.ytying.ytblog.constants.SpKey;
import com.ytying.ytblog.utils.JsonUtil;
import com.ytying.ytblog.utils.SpUtil;

/**
 * Created by UKfire on 16/4/13.
 */
public class Account {

    public static void saveAccount(MyInfo account){
        SpUtil.saveString(SpKey.MY_INFO, JsonUtil.Object2Json(account));
    }

    public static String getFunId(){
        JSONObject jsonObject = JsonUtil.string2JsonObject(SpUtil.loadString(SpKey.MY_INFO));
        return jsonObject.getString("funId");
    }

    public static String getPassword(){
        JSONObject jsonObject = JsonUtil.string2JsonObject(SpUtil.loadString(SpKey.MY_INFO));
        return jsonObject.getString("password");
    }
}
