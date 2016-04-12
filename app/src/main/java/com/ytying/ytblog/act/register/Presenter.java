package com.ytying.ytblog.act.register;

import android.os.Handler;

import com.ytying.ytblog.base.BasePresenter;
import com.ytying.ytblog.network.CallBack;
import com.ytying.ytblog.network.Network;
import com.ytying.ytblog.network.RequestFactory;
import com.ytying.ytblog.network.Response;

/**
 * Created by UKfire on 16/3/8.
 */
public class Presenter extends BasePresenter<IView> {

    public Presenter(IView view) {
        super(view);
    }

    public void onRegister(String funId, String password, String pass_confirm, String nickname, String phone) {

        final String fId = funId;
        final String pass = password;
        final String pass_con = pass_confirm;
        final String nick = nickname;
        final String pho = phone;

        if (fId.equals("") || pass.equals("") || pass_con.equals("") || nick.equals("") || pho.equals("")) {
            getView().showToast("请填写完整");
        } else if (!pass.equals(pass_con)) {
            getView().showToast("两次密码不一样");
        } else {
            getView().showLoading("正在注册请稍后");
            Network.post(RequestFactory.Register(fId, pass, nick, pho), new Handler(), new CallBack() {
                @Override
                public void onCommon(Response response) {
                    getView().stopLoading();
                }

                @Override
                public void onError(Response response) {
                    getView().showToast("注册失败");
                }

                @Override
                public void onSuccess(Response response) {
                    getView().showToast("注册成功");
                    getView().gotoLogin();
                }
            });

        }
    }
}
