package com.ytying.ytblog.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ytying.ytblog.F;
import com.ytying.ytblog.constants.SpKey;

/**
 * Created by UKfire on 16/3/10.
 */
public class SpUtil {

    public static String loadString(SpKey key) {
        SharedPreferences sp = F.AppContext.getSharedPreferences(key.getType(), Context.MODE_PRIVATE);
        return sp.getString(key.getKey(), "");
    }

    public static void saveString(SpKey key, String value) {
        SharedPreferences sp = F.AppContext.getSharedPreferences(key.getType(), Context.MODE_PRIVATE);
        sp.edit().putString(key.getKey(), value).commit();
    }

    public static void clearUserSp() {
        SharedPreferences sp = F.AppContext.getSharedPreferences("user",Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
