package com.ytying.ytblog.model;

import java.io.Serializable;

/**
 * Created by UKfire on 16/3/10.
 */
public class DoObject implements Serializable{

    private static final long serialVersionUID = -5289351590691696391L;

    protected Boolean tokay(Boolean b){
        if(b == null)
            return false;
        else
            return b;
    }

    protected Integer tokay(Integer i){
        if(i == null)
            return 0;
        else
            return i;
    }

    protected Float tokay(Float f){
        if(f == null)
            return 0.0f;
        else
            return f;
    }

    protected String tokay(String s){
        if(s == null)
            return "";
        else
            return s;
    }

    protected Long tokay(Long l){
        if(l == null)
            return 0l;
        else
            return l;
    }
}
