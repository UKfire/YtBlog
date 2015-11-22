package com.ytying.ytblog.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by UKfire on 15/11/22.
 */
public abstract class BaseRunnable implements Runnable {

    private Handler handler;

    public BaseRunnable(Handler handler){
        this.handler = handler;
    }

    protected Handler getHandler(){
        return handler;
    }

    protected void sendMessage(int what,String s){
        Message msg = new Message();
        msg.what = what;
        Bundle bundle = new Bundle();
        if(s != null && !s.equals(""))
            bundle.putString("response",s);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    protected void sendMessage(int what){
        sendMessage(what,null);
    }

    protected void sendMessage(String s){
        sendMessage(0,s);
    }

}
