package com.ytying.ytblog.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ytying.ytblog.act.widget.StandardProgressDialog;

/**
 * Created by ukfire on 15-11-20.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseIView{

    public final static int RESULT_LOGOUT = 1001;

    //初始化数据
    protected abstract void initData();

    protected StandardProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initData();
    }



}
