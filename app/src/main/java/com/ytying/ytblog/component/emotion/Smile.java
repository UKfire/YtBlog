package com.ytying.ytblog.component.emotion;

/**
 * Created by UKfire on 16/3/13.
 */
public class Smile {

    private int resId;
    private String tag;

    public Smile(int resId, String tag) {
        this.resId = resId;
        this.tag = tag;
    }


    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
