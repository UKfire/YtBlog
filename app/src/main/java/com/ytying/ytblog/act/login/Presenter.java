package com.ytying.ytblog.act.login;

import android.os.Handler;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.ytying.ytblog.base.BasePresenter;
import com.ytying.ytblog.constants.SpKey;
import com.ytying.ytblog.model.Account;
import com.ytying.ytblog.model.MyInfo;
import com.ytying.ytblog.model.domin.DbUser;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.network.CallBack;
import com.ytying.ytblog.network.Network;
import com.ytying.ytblog.network.RequestFactory;
import com.ytying.ytblog.network.Response;
import com.ytying.ytblog.utils.JsonUtil;
import com.ytying.ytblog.utils.SpUtil;

/**
 * Created by UKfire on 16/4/12.
 */
public class Presenter extends BasePresenter<IView> {

    Handler handler = new Handler();

    public Presenter(IView view) {
        super(view);
    }

    public void onLogin(String funId, final String password) {
        final String fId = funId;
        final String pass = password;
        if (fId.equals("") || pass.equals(""))
            getView().showToast("请填写完整");
        else {
            getView().showLoading("正在登录请稍后");
            Network.post(RequestFactory.Login(fId, pass), handler, new CallBack() {
                @Override
                public void onCommon(Response response) {

                }

                @Override
                public void onError(Response response) {
                    getView().showToast("登录失败");
                    getView().stopLoading();
                }

                @Override
                public void onSuccess(Response response) {
                    //Sp存funId
                    SpUtil.saveString(SpKey.USER_FUNID, fId);

                    //把自己信息存库
                    DbUser.getInstance().saveUser(JsonUtil.Json2T(response.getDataString(), User.class, new User()));

                    //环信登录
                    IMLogin(fId, password);
                }
            });
        }
    }

    /**
     * IM登录
     * @param funId 用户名
     * @param password 密码
     */
    public void IMLogin(final String funId, final String password){
        LoginInfo info = new LoginInfo(funId,password); // config...

        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {

                        getView().stopLoading();

                        //缓存Account
                        MyInfo myInfo = new MyInfo();
                        myInfo.setFunId(funId);
                        myInfo.setPassword(password);
                        Account.saveAccount(myInfo);

                        SpUtil.saveString(SpKey.MY_LOGININFO,JsonUtil.Object2Json(loginInfo));

                        getView().showToast("登录成功");
                        getView().gotoMain();
                    }

                    @Override
                    public void onFailed(int i) {
                        getView().stopLoading();
                        getView().showToast("登录失败");
                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }
}
