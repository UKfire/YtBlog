package com.ytying.ytblog.model;

import com.ytying.ytblog.model.domin.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UKfire on 16/3/15.
 */
public class Design extends DoObject {

    private int attention;
    private String content;
    private List<String> designImage;
    private User user;

    public int getAttention() {
        return tokay(attention);
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public String getContent() {
        return tokay(content);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getDesignImage() {
        if(designImage == null)
            designImage = new ArrayList<>();
        return designImage;
    }

    public void setDesignImage(List<String> designImage) {
        this.designImage = designImage;
    }

    public User getUser() {
        if(user == null)
            user = new User();
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
