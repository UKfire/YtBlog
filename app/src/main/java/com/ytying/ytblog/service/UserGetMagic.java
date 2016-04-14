package com.ytying.ytblog.service;

import android.util.Log;

import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.network.Response;

import java.util.LinkedList;

/**
 * Created by UKfire on 16/4/14.
 */
public class UserGetMagic {

    private static final String TAG = "UserGetMagic";
    private static Object lock = new Object();
    private static LinkedList<UserGetThread> list = new LinkedList<>();

    public static void callTask(String funId,UserGetThread.UpdateUIListener listener){
        if(listener == null){
            return;
        }

        Log.v(TAG,"callTask_uid==" + funId);

        UserGetThread getUserInfoThread = null;
        synchronized (lock){
            for(UserGetThread thread : list){
                if(thread.getFunId() == funId){
                    getUserInfoThread = thread;
                }
            }
        }

        if(getUserInfoThread != null){
            Log.v(TAG,"callTask_thread_already_exists_" + funId);
            getUserInfoThread.addUIListener(listener);
        }else{
            Log.v(TAG,"callTask_open_new_thread_" + funId);
            getUserInfoThread = new UserGetThread(funId, new UserGetThread.ThreadGetListener() {
                @Override
                public void onSuccess(User user) {
                    try{
                        synchronized (lock){
                            for(UserGetThread thread : list){
                                if(thread.getFunId().equals(user.getFunId())){
                                    list.remove(thread);
                                }
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(String funId, Response response) {
                    try {
                        synchronized (lock) {
                            for (UserGetThread thread : list) {
                                if (thread.getFunId().equals(funId)) {
                                    list.remove(thread);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onInterrupted(String funId) {
                    try {
                        //在队列中取消本线程
                        synchronized (lock) {
                            for (UserGetThread thread : list) {
                                if (thread.getFunId().equals(funId)) {
                                    list.remove(thread);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            getUserInfoThread.addUIListener(listener);
            list.add(getUserInfoThread);
            getUserInfoThread.start();
        }
    }

}
