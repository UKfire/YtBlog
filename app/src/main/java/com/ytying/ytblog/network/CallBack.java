package com.ytying.ytblog.network;

/**
 * Created by UKfire on 16/3/11.
 */
public interface CallBack {
    void onCommon(Response response);

    void onError(Response response);

    void onSuccess(Response response);
}
