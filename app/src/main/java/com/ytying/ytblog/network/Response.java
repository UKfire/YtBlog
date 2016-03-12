package com.ytying.ytblog.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ytying.ytblog.model.DoObject;
import com.ytying.ytblog.utils.JsonUtil;

/**
 * Created by UKfire on 16/3/10.
 */
public class Response extends DoObject {

    protected final static int CODE_SUCCESS = 0;
    protected final static int CODE_NOT_YET = -9;


    private int code = CODE_NOT_YET;
    private String data;

    public boolean isSucc() {
        if (code == CODE_SUCCESS)
            return true;
        else
            return false;
    }

    /**
     * 您可能处于离线，请连接网络
     *
     * @return
     */
    public static Response createOfflineError() {
        Response response = new Response();
        response.code = -1;
        return response;
    }

    /**
     * 网络状况不好，请重试
     *
     * @return
     */
    public static Response createHostError() {
        Response response = new Response();
        response.code = -2;
        return response;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataString() {
        return tokay(data);
    }

    public JSONArray getDataJsonArray() {
        return JsonUtil.string2Array(data);
    }

    public JSONObject getDataJsonObject() {
        return JsonUtil.string2JsonObject(data);
    }

    public String toJsonString() {
        JSONObject jObj = new JSONObject();
        jObj.put("code", getCode());
        jObj.put("data", getDataString());
        return jObj.toJSONString();
    }

}
