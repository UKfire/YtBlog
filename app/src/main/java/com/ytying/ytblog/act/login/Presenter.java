package com.ytying.ytblog.act.login;

import android.os.Handler;

import com.ytying.ytblog.base.BasePresenter;
import com.ytying.ytblog.constants.SpKey;
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

    public void onLogin(String funId, String password) {
        final String fId = funId;
        final String pass = password;
        if (fId.equals("") || pass.equals(""))
            getView().showToast("请填写完整");
        else {
            getView().showLoading("正在登录请稍后");
            Network.post(RequestFactory.Login(fId, pass), handler, new CallBack() {
                @Override
                public void onCommon(Response response) {
                    getView().stopLoading();
                }

                @Override
                public void onError(Response response) {
                    getView().showToast("登录失败");
                }

                @Override
                public void onSuccess(Response response) {
                    SpUtil.saveString(SpKey.USER_FUNID, fId);
                    DbUser.getInstance().saveUser(JsonUtil.Json2T(response.getDataString(), User.class, new User()));
                    getView().showToast("登录成功");
                    getView().gotoMain();
                }
            });
        }
    }
}
