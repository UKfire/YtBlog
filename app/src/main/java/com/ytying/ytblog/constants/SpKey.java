package com.ytying.ytblog.constants;

/**
 * Created by UKfire on 16/3/10.
 */
public enum SpKey {

    USER_FUNID("USER_FUNID","user"),
    DESIGN_LIST("DESIGN_LIST", "user"),
    MY_INFO("MY_INFO","user"),
    MY_LOGININFO("MY_LOGININFO","user");

    private String key;
    private String type;
    private String append = "";

    public String getKey() {
        return key + append;
    }

    private SpKey(String key, String type) {
        this.key = key;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public SpKey setAppend(String s) {
        append = s;
        return this;
    }
}
