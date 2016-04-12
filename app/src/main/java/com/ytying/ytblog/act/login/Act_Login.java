package com.ytying.ytblog.act.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.register.Act_Register;
import com.ytying.ytblog.activity.main.MainActivity;
import com.ytying.ytblog.base.BaseActivity;

/**
 * Created by UKfire on 16/3/9.
 */
public class Act_Login extends BaseActivity implements IView{

    Button login;
    AppCompatEditText funId;
    AppCompatEditText password;
    TextView goto_register;

    Presenter presenter;

    Handler handler = new Handler();

    @Override
    protected void initData() {
        presenter = new Presenter(this);
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

                presenter.onLogin(funId.getText().toString(),password.getText().toString());

            }
        });

    }

    @Override
    public void gotoMain() {
        Intent intent = new Intent(Act_Login.this, MainActivity.class);
        startActivity(intent);
        Act_Login.this.finish();
    }
}
