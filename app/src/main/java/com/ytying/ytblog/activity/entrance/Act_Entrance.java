package com.ytying.ytblog.activity.entrance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ytying.ytblog.MyUser;
import com.ytying.ytblog.R;
import com.ytying.ytblog.act.login.Act_Login;
import com.ytying.ytblog.activity.main.MainActivity;
import com.ytying.ytblog.constants.SpKey;
import com.ytying.ytblog.utils.SpUtil;
import com.ytying.ytblog.utils.ThreadUtil;

/**
 * Created by UKfire on 16/3/16.
 */
public class Act_Entrance extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_entrance);

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!MyUser.loadUid().equals("") && !SpUtil.loadString(SpKey.MY_INFO).equals("")) {
                    gotoMain();
                } else {
                    gotoLogin();
                }
            }
        });
    }

    private void gotoLogin() {
        startActivity(new Intent(this, Act_Login.class));
        finish();
    }

    private void gotoMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
