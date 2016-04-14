package com.ytying.ytblog.service;

import android.util.Log;

import com.ytying.ytblog.model.domin.DbUser;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.network.Network;
import com.ytying.ytblog.network.RequestFactory;
import com.ytying.ytblog.network.Response;
import com.ytying.ytblog.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UKfire on 16/4/14.
 */
public class UserGetThread extends Thread{

    private static final String TAG = "UserGetThread";

    ThreadGetListener threadGetListener;
    List<UpdateUIListener> uiListenerList = new ArrayList<>();

    private String funId;

    public UserGetThread(String funId,ThreadGetListener listener){
        this.funId = funId;
        this.threadGetListener = listener;
    }

    public String getFunId(){
        return this.funId;
    }


    public void addUIListener(UpdateUIListener l){
        uiListenerList.add(l);
    }

    public interface UpdateUIListener{
        void onSuccess(User user);
        void onFail(Response response);
    }

    public interface ThreadGetListener{
        void onSuccess(User user);
        void onFail(String funId,Response response);
        void onInterrupted(String funId);
    }

    public void run(){
        Log.v(TAG,"run");
        try{
            int tryNum = 3;
            while(tryNum -- > 0){
                Log.v(TAG,"先找数据库" + funId);
                User user = DbUser.getInstance().loadUser(funId);
                if(user.getName().length() > 0){
                    Log.v(TAG, "数据库里找到了" + funId);
                    threadGetListener.onSuccess(user);
                    for(UpdateUIListener uiListener : uiListenerList){
                        uiListener.onSuccess(user);
                    }
                    break;
                }

                Log.v(TAG,"再去网上拉" + funId);
                Response response = Network.post(RequestFactory.GetUserDetail(funId));
                if(response.isSucc()){
                    user = JsonUtil.Json2T(response.getDataString(),User.class,new User());
                    DbUser.getInstance().saveUser(user);
                    threadGetListener.onSuccess(user);
                    for(UpdateUIListener uiListener : uiListenerList){
                        uiListener.onSuccess(user);
                    }
                    break;
                }else{
                    threadGetListener.onFail(funId,response);
                    if(tryNum == 0){
                        for(UpdateUIListener uiListener : uiListenerList){
                            uiListener.onFail(response);
                        }
                    }
                }

            }
        }catch (Exception e){
            threadGetListener.onInterrupted(funId);
            for (UpdateUIListener uiListener : uiListenerList) {
                uiListener.onFail(new Response());
            }
        }
    }

}
