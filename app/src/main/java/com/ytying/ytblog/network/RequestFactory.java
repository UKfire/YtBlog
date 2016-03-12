package com.ytying.ytblog.network;

import java.util.HashMap;

/**
 * Created by UKfire on 15/11/24.
 */
public class RequestFactory {

    //TODO:推进后台api
    private static final String ROOT_URL = "http://172.19.142.128:8000/hzx/";

    private static final String Login = "login/";
    private static final String Register = "register/";

    public static Request createRequest(String url) {
        return new Request(ROOT_URL + url, new HashMap<String, String>());
    }

    public static Request Login(String funId, String password) {
        return createRequest(Login)
                .add("funId", funId + "")
                .add("password", password + "");
    }

    public static Request Register(String funId, String password, String nickname, String sex, String email) {
        return createRequest(Register)
                .add("funId", funId + "")
                .add("password", password + "")
                .add("nickname", nickname + "")
                .add("email", email + "")
                .add("designer", "0")
                .add("sex", sex + "")
                .add("createTime", System.currentTimeMillis() + "")
                .add("phoneNumber", "")
                .add("qqNumber", "")
                .add("headImage", "")
                .add("motto", "")
                .add("backImage", "")
                .add("province", "")
                .add("city", "");

    }

}
