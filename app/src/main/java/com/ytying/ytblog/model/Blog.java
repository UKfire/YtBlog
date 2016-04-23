package com.ytying.ytblog.model;

/**
 * Created by UKfire on 16/4/17.
 */
public class Blog extends DoObject {
    private String _id;
    private String funId;
    private String content;
    private String image;
    private String createTime;

    public String get_id() {
        return tokay(_id);
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFunId() {
        return tokay(funId);
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public String getContent() {
        return tokay(content);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return tokay(image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateTime() {
        return tokay(createTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
