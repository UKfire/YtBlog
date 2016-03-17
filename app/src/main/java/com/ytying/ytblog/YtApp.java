package com.ytying.ytblog;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.ytying.ytblog.act.login.Act_Login;
import com.ytying.ytblog.model.domin.DaoMaster;
import com.ytying.ytblog.model.domin.DaoSession;
import com.ytying.ytblog.utils.SpUtil;

/**
 * Created by UKfire on 15/11/20.
 */
public class YtApp extends Application {

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private static Bus ottoBus;

    @Override
    public void onCreate() {
        super.onCreate();
        F.AppContext = getApplicationContext();
    }

    public static void logout(Activity act) {

        SpUtil.clearUserSp();

        act.startActivity(new Intent(act, Act_Login.class));
        act.finish();
    }

    /**
     * 取得DaoMaster
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, F.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static Bus getOtto() {
        if (ottoBus == null)
            ottoBus = new Bus(ThreadEnforcer.MAIN);
        return ottoBus;
    }

}
