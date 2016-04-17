package com.ytying.ytblog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by UKfire on 16/4/17.
 */
public class DateUtil {
    private static final long INTERVAL_IN_MILLISECONDS = 1200000L;

    public static String getTimestampString(long time){
        return new SimpleDateFormat("M月d日 H:m").format(new Date(time));
    }

    public static boolean isCloseEnough(long var0, long var2) {
        long var4 = var0 - var2;
        if(var4 < 0L) {
            var4 = -var4;
        }

        return var4 < 120000L;
    }


}
