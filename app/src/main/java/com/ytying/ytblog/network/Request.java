package com.ytying.ytblog.network;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by UKfire on 15/11/24.
 */
public class Request {

    private String url;
    private Map<String, String> map;

    private String file;

    public Request(String url, Map<String, String> map) {
        this.url = url;
        this.map = map;
    }

    public Request(String url, String file) {
        this.url = url;
        this.file = file;
        this.map = new HashMap<String, String>();
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Request add(String key, String val) {
        this.getMap().put(key, val);
        return this;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
