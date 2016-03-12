package com.ytying.ytblog.act.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.login.Act_Login;
import com.ytying.ytblog.base.BaseActivity;
import com.ytying.ytblog.network.CallBack;
import com.ytying.ytblog.network.Network;
import com.ytying.ytblog.network.RequestFactory;
import com.ytying.ytblog.network.Response;
import com.ytying.ytblog.utils.DoUtil;

/**
 * Created by UKfire on 16/3/8.
 */
public class Act_Register extends BaseActivity implements IView {

    Presenter presenter;
    String sex = "";

    Button register;
    TextView goto_login;
    RadioGroup group;
    AppCompatEditText funId, password, email, nickname, pass_confirm;

    @Override
    protected void initData() {
        presenter = new Presenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        funId = (AppCompatEditText) findViewById(R.id.funId_register);
        password = (AppCompatEditText) findViewById(R.id.password_register);
        email = (AppCompatEditText) findViewById(R.id.email_register);
        pass_confirm = (AppCompatEditText) findViewById(R.id.password_confirm_register);
        nickname = (AppCompatEditText) findViewById(R.id.nickname_register);
        goto_login = (TextView) findViewById(R.id.goto_login);
        register = (Button) findViewById(R.id.register);
        group = (RadioGroup) findViewById(R.id.sex_group);

        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_Register.this, Act_Login.class);
                startActivity(intent);
                Act_Register.this.finish();
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                RadioButton button = (RadioButton) Act_Register.this.findViewById(id);
                if (button.getText().toString().equals("男"))
                    sex = "male";
                else
                    sex = "female";
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fId = funId.getText().toString();
                final String pass = password.getText().toString();
                final String pass_con = pass_confirm.getText().toString();
                final String nick = nickname.getText().toString();
                final String emi = email.getText().toString();

                if (fId.equals("") || pass.equals("") || pass_con.equals("") || nick.equals("") || emi.equals("") || sex.equals("")) {
                    Toast.makeText(Act_Register.this, "请填写完整", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(pass_con)) {
                    Toast.makeText(Act_Register.this, "两次密码不一样", Toast.LENGTH_SHORT).show();
                } else {
                    showLoading("正在注册请稍后");
                    Network.post(RequestFactory.Register(fId, pass, nick, sex, emi), new Handler(), new CallBack() {
                        @Override
                        public void onCommon(Response response) {
                            stopLoading();
                        }

                        @Override
                        public void onError(Response response) {
                            DoUtil.showToast(Act_Register.this, "注册失败");
                        }

                        @Override
                        public void onSuccess(Response response) {
                            DoUtil.showToast(Act_Register.this, "注册成功");
                        }
                    });

                }
            }
        });
    }
}
