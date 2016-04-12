package com.ytying.ytblog.network;

import java.util.HashMap;

/**
 * Created by UKfire on 15/11/24.
 */
public class RequestFactory {

    private static final String ROOT_URL = "http://172.19.199.205:1234/";

    //User
    private static final String Login = "login/";
    private static final String Register = "register/";
    private static final String GetDetailUser = "getUserDetail/";
    private static final String HeadImage = "headImage/";
    private static final String BackImage = "backImage/";

    //Blog
    private static final String AddBlogPic = "addBlogPic/";
    private static final String AddBlog = "addBlog/";
    private static final String GetBlogList = "getBlogList/";
    private static final String GetUserBlogList = "getUserBlogList/";

    //Message
    private static final String SendMessage = "sendMessage/";
    private static final String ReceiveMessage = "receiveMessage/";

    //Goddess
    private static final String AddGoddessPic = "addGoddessPic/";
    private static final String AddGoddess = "addGoddess/";
    private static final String GetGoddessList = "getGoddessList/";
    private static final String ZanGoddess = "zanGoddess/";


    //User
    public static Request createRequest(String url) {
        return new Request(ROOT_URL + url, new HashMap<String, String>());
    }

    public static Request Login(String funId, String password) {
        return createRequest(Login)
                .add("funId", funId + "")
                .add("password", password + "");
    }

    public static Request Register(String funId, String password, String nickname, String phone) {
        return createRequest(Register)
                .add("funId", funId + "")
                .add("password", password + "")
                .add("name", nickname + "")
                .add("phone", phone+ "");
    }

    public static Request GetUserDetail(String funId) {
        return createRequest(GetDetailUser)
                .add("funId", funId + "");
    }


}
