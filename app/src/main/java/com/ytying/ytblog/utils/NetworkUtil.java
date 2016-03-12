package com.ytying.ytblog.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ytying.ytblog.F;

/**
 * Created by UKfire on 15/11/24.
 */
public class NetworkUtil {

    public static enum NetState{
        WIFI,MOBILE,NONE;
    }

    public static NetState CheckNetworkState(){
        try {
            ConnectivityManager manager = (ConnectivityManager) F.AppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo.State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            if(mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
                return NetState.MOBILE;
            else if(wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
                return NetState.WIFI;
            else
                return NetState.NONE;
        }catch (Exception e){
            e.printStackTrace();
            return NetState.NONE;
        }
    }

}
