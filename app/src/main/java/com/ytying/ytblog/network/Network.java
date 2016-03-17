package com.ytying.ytblog.network;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.ytying.ytblog.MyUser;
import com.ytying.ytblog.utils.JsonUtil;
import com.ytying.ytblog.utils.NetworkUtil;
import com.ytying.ytblog.utils.ThreadUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by UKfire on 15/11/24.
 */
public class Network {

    private static final String TAG = "DoNetWork";

    public static Response post(Request request) {
        return executebase(request.getUrl(), request.getMap());
    }

    public static void post(final Request request, final Handler handler, final CallBack callBack) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                final Response response = executebase(request.getUrl(), request.getMap());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onCommon(response);
                        if (response.isSucc()) {
                            callBack.onSuccess(response);
                        } else
                            callBack.onError(response);
                    }
                });
            }
        });
    }

    /**
     * @param request
     * @param handler
     * @param callback
     */
    public static void uploadFile(final Request request, final Handler handler, final CallBack callback) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                final Response response = executeFile(request.getUrl(), request.getFile());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        callback.onCommon(response);
                        if (response.isSucc())
                            callback.onSuccess(response);
                        else
                            callback.onError(response);
                    }
                });
            }
        });

    }

    public static Response uploadFile(Request request) {
        return executeFile(request.getUrl(), request.getFile());
    }


    public static Response executebase(String url, Map<String, String> map) {
        if (NetworkUtil.CheckNetworkState() == NetworkUtil.NetState.NONE)
            return Response.createOfflineError();
        String[] ary = url.split("/");
        String method = ary[ary.length - 1];
        Log.d(TAG, method + "_" + "url==" + url);
        Log.d(TAG, method + "_" + "params==" + map.toString());
        String json = new String();
        try {
            json = HttpUtil.post(url, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, method + "_" + "result==" + json);

        Response response = JsonUtil.Json2T(json, Response.class, new Response());
        if (null == response)
            response = Response.createHostError();

        return response;
    }


    /**
     * 上传图片
     *
     * @param url
     * @param file
     * @return
     */
    private static Response executeFile(String url, String file) {

        //先看联网了不？没联网还搞个屁，直接return呗
        if (NetworkUtil.CheckNetworkState() == NetworkUtil.NetState.NONE)
            return Response.createOfflineError();

        Log.d(TAG, "url==" + url);
        Log.d(TAG, "file==" + file + "==" + MyUser.loadUid());
        String json = HttpUtil.upload(url, MyUser.loadUid(), file);
        Log.d(TAG, "result==" + json);

        try {
            Response response = JSON.parseObject(json, Response.class);

            if (null == response) {
                return Response.createHostError();
            } else
                return response;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.createHostError();
        }
    }


    /**
     * 上传Design，因为服务端比较蠢，所以没办法延用以前的封装＝＝，西瓜是狗！
     *
     * @param content
     * @param list
     * @return
     */
    public static void addDesign(final String content, final List<String> list, final Handler handler, final CallBack callback) {

        final String url = "http://172.19.142.128:8000/hzx/addDesign/";

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                Response response = null;

                if (NetworkUtil.CheckNetworkState() == NetworkUtil.NetState.NONE)
                    response = Response.createOfflineError();
                else {
                    String json = HttpUtil.uploadDesign(url, MyUser.loadUid(), content, list);

                    try {
                        response = JSON.parseObject(json, Response.class);
                    } catch (Exception e) {
                        response = Response.createHostError();
                        e.printStackTrace();
                    }
                    Log.d(TAG, "url==" + url);
                    Log.d(TAG, "addDesign_result==" + json);
                }
                if (response == null)
                    response = Response.createHostError();
                final Response finalResponse = response;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCommon(finalResponse);
                        if (finalResponse.isSucc())
                            callback.onSuccess(finalResponse);
                        else
                            callback.onError(finalResponse);
                    }
                });

            }
        });
    }

    public static void addDesignImageAndroid(final int id, final List<String> list, final Handler handler, final CallBack callback) {

        final String url = "http://172.19.142.128:8000/hzx/addDesignImageAndroid/";

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                Response response = null;

                if (NetworkUtil.CheckNetworkState() == NetworkUtil.NetState.NONE)
                    response = Response.createOfflineError();
                else {
                    String json = HttpUtil.uploadDesignImageAndroid(url, id, list);

                    try {
                        response = JSON.parseObject(json, Response.class);
                    } catch (Exception e) {
                        response = Response.createHostError();
                        e.printStackTrace();
                    }
                    Log.d(TAG, "url==" + url);
                    Log.d(TAG, "addDesignImageAndroid_result==" + json);
                }
                if (response == null)
                    response = Response.createHostError();
                final Response finalResponse = response;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCommon(finalResponse);
                        if (finalResponse.isSucc())
                            callback.onSuccess(finalResponse);
                        else
                            callback.onError(finalResponse);
                    }
                });

            }
        });
    }


}
