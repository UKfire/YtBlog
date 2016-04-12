package com.ytying.ytblog.act.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.login.Act_Login;
import com.ytying.ytblog.base.BaseActivity;

/**
 * Created by UKfire on 16/3/8.
 */
public class Act_Register extends BaseActivity implements IView {

    Presenter presenter;
    String sex = "";

    Button register;
    TextView goto_login;
    AppCompatEditText funId, password, phone, nickname, pass_confirm;

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
        phone = (AppCompatEditText) findViewById(R.id.phone_register);
        pass_confirm = (AppCompatEditText) findViewById(R.id.password_confirm_register);
        nickname = (AppCompatEditText) findViewById(R.id.nickname_register);
        goto_login = (TextView) findViewById(R.id.goto_login);
        register = (Button) findViewById(R.id.register);

        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_Register.this, Act_Login.class);
                startActivity(intent);
                Act_Register.this.finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegister(funId.getText().toString(), password.getText().toString(), pass_confirm.getText().toString(), nickname.getText().toString(), phone.getText().toString());
            }
        });
    }

    @Override
    public void gotoLogin() {
        startActivity(new Intent(this,Act_Login.class));
        this.finish();
    }
}
