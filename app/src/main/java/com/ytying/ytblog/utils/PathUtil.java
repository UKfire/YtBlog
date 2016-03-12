package com.ytying.ytblog.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by UKfire on 16/3/10.
 * <p/>
 * sdcard/com.ytying.ytblog/
 * sdcard/com.ytying.ytblog/Download
 * sdcard/com.ytying.ytblog/Temp
 * sdcard/com.ytying.ytblog/Save
 */
public class PathUtil {

    public static String getAppRootPath() {
        if (!new File(PathUtil.getPhoneRootFilePath() + "com.ytying.ytblog/").exists())
            new File(PathUtil.getPhoneRootFilePath() + "com.ytying.ytblog/").mkdirs();
        return PathUtil.getPhoneRootFilePath() + "com.ytying.ytblog/";
    }

    public static String getAppFileSavePath() {
        if (!new File(PathUtil.getAppRootPath() + "Download/").exists())
            new File(PathUtil.getAppRootPath() + "Download/").mkdirs();
        return PathUtil.getAppRootPath() + "Download/";
    }

    public static String getAppTempPath() {
        if (!new File(PathUtil.getAppRootPath() + "Temp/").exists())
            new File(PathUtil.getAppRootPath() + "Temp/").mkdirs();
        return PathUtil.getAppRootPath() + "Temp/";
    }

    public static String getAppSavePath() {
        if (!new File(PathUtil.getAppRootPath() + "Save/").exists())
            new File(PathUtil.getAppRootPath() + "Save/").mkdirs();
        return PathUtil.getAppRootPath() + "Save/";
    }

    //私有方法
    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return true;
    }

    public static String getPhoneRootFilePath() {
        if (hasSDCard())
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        else
            return Environment.getDataDirectory().getAbsolutePath() + "/data/";
    }

    public enum Scheme {

    }
}
