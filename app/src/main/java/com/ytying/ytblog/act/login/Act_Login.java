package com.ytying.ytblog.act.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.register.Act_Register;
import com.ytying.ytblog.activity.main.MainActivity;
import com.ytying.ytblog.base.BaseActivity;
import com.ytying.ytblog.constants.SpKey;
import com.ytying.ytblog.model.domin.DbUser;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.network.CallBack;
import com.ytying.ytblog.network.Network;
import com.ytying.ytblog.network.RequestFactory;
import com.ytying.ytblog.network.Response;
import com.ytying.ytblog.utils.DoUtil;
import com.ytying.ytblog.utils.JsonUtil;
import com.ytying.ytblog.utils.SpUtil;

/**
 * Created by UKfire on 16/3/9.
 */
public class Act_Login extends BaseActivity {

    Button login;
    AppCompatEditText funId;
    AppCompatEditText password;
    TextView goto_register;

    Handler handler = new Handler();

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        login = (Button) findViewById(R.id.login);
        funId = (AppCompatEditText) findViewById(R.id.funId_login);
        password = (AppCompatEditText) findViewById(R.id.password_login);
        goto_register = (TextView) findViewById(R.id.goto_register);

        goto_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_Login.this, Act_Register.class);
                startActivity(intent);
                Act_Login.this.finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fId = funId.getText().toString();
                final String pass = password.getText().toString();
                if (fId.equals("") || pass.equals(""))
                    Toast.makeText(Act_Login.this, "请填写完整", Toast.LENGTH_SHORT).show();
                else {
                    showLoading("正在登录请稍后");
                    Network.post(RequestFactory.Login(fId, pass), handler, new CallBack() {
                        @Override
                        public void onCommon(Response response) {
                            stopLoading();
                        }

                        @Override
                        public void onError(Response response) {
                            DoUtil.showToast(Act_Login.this, "登录失败");
                        }

                        @Override
                        public void onSuccess(Response response) {
                            SpUtil.saveString(SpKey.USER_FUNID, fId);
                            DbUser.getInstance().saveUser(JsonUtil.Json2T(response.getDataString(), User.class, new User()));
                            DoUtil.showToast(Act_Login.this, "登录成功");
                            gotoMain();
                        }
                    });
                }
            }
        });
    }

    private void gotoMain() {
        Intent intent = new Intent(Act_Login.this, MainActivity.class);
        startActivity(intent);
        Act_Login.this.finish();
    }
}
