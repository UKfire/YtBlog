package com.ytying.ytblog.constants;

import com.ytying.ytblog.act.contacts.InterChat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by UKfire on 16/4/16.
 */
public class GSession {
    private Map _objectContainer;

    private static GSession session;

    private GSession(){
        _objectContainer = new HashMap();
    }

    public static GSession getSession(){
        if(session == null){
            session = new GSession();
            return session;
        }else{
            return session;
        }
    }

    private void put(Object key,Object value){
        _objectContainer.put(key,value);
    }

    private Object get(Object key){
        Object obj = _objectContainer.get(key);
        return obj;
    }

    /**
     * 传递聊天
     * @param one
     */
    public void putInterChat(InterChat one){
        put("interChat",one);
    }

    public InterChat getInterChat(){
        return (InterChat) get("interChat");
    }
}
