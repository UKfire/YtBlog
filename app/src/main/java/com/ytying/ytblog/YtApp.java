package com.ytying.ytblog;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;

import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.ytying.ytblog.act.contacts.MessageActivity;
import com.ytying.ytblog.act.login.Act_Login;
import com.ytying.ytblog.constants.SpKey;
import com.ytying.ytblog.model.domin.DaoMaster;
import com.ytying.ytblog.model.domin.DaoSession;
import com.ytying.ytblog.utils.DimenUtil;
import com.ytying.ytblog.utils.JsonUtil;
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
        NIMClient.init(this, loginInfo(), options());
    }

    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MessageActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.drawable.ic_menu_back;
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = DimenUtil.getWidth() / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfoProvider.UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public int getDefaultIconResId() {
                return R.mipmap.icon_addpic_normal;
            }

            @Override
            public Bitmap getTeamIcon(String tid) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }
        };
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        String s = SpUtil.loadString(SpKey.MY_INFO);
        String info = SpUtil.loadString(SpKey.MY_LOGININFO);
        if (s.equals("") || info.equals(""))
            return null;
        else {
            JSONObject jsonObject = JsonUtil.string2JsonObject(s);
            String funId = jsonObject.getString("funId");
            String password = jsonObject.getString("password");
            LoginInfo loginInfo = JsonUtil.Json2T(info, LoginInfo.class, new LoginInfo(funId, password));
            return loginInfo;
        }
    }

    public static void logout(Activity act) {

        SpUtil.clearUserSp();
        NIMClient.getService(AuthService.class).logout();
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
