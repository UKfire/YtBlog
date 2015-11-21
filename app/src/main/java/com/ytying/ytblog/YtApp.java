package com.ytying.ytblog;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by UKfire on 15/11/20.
 */
public class YtApp extends Application {

    private static Bus ottoBus;

    public static Bus getOtto(){
        if(ottoBus == null)
            ottoBus = new Bus(ThreadEnforcer.MAIN);
        return ottoBus;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        F.AppContext = this;
    }
}
