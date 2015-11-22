package com.ytying.ytblog.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by UKfire on 15/11/22.
 */
public abstract class BaseHandler <T extends BasePresenter> extends Handler{

    public static final int MSG_UPDATE = 1;
    public static final int MSG_DOWN_FINISH = 2;
    public static final int MSG_UP_FINISH = 3;

    T presenter;

    public BaseHandler(T presenter){
        super(Looper.myLooper());
        this.presenter = presenter;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        String s = null;
        try {
            s = msg.getData().getString("response");
            if(s == null)
                s = "";
        }catch (Exception e){
            e.printStackTrace();
            s = "";
        }
        handleMessage(s,msg);
    }

    //如果成功，想干什么干什么，如果失败就老老实实showToast
    public void handleMessage(String s,Message msg){
        if(s.equals("success"))
            handleSuccess(s,msg);
        else
            getPresenter().getView().showToast(s);
    }

    abstract protected void handleSuccess(String s,Message msg);

    public T getPresenter(){
        return presenter;
    }

    public BaseIView getView(){
        return presenter.getView();
    }

}
