package com.ytying.ytblog.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.StandardProgressDialog;
import com.ytying.ytblog.utils.StringUtil;

/**
 * Created by ukfire on 15-11-20.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseIView{

    public final static int RESULT_LOGOUT = 1001;
    public boolean finish = false;

    //初始化数据
    protected abstract void initData();

    protected StandardProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initData();
        if(finish)
            return;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(finish)
            return;
    }

    @Override
    public void finish() {
        finish=true;
        super.finish();
    }

    public void finish(int result) {
        finish=true;
        setResult(result);
        super.finish();
    }

    @Override
    public void showToast(String toast) {
        if(!StringUtil.isBlank(toast)){
            try{
                Toast.makeText(this,toast,Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showLoading(String msg) {
        if(progressDialog != null)
            progressDialog.cancel();
        progressDialog = new StandardProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        try {
            progressDialog.cancel();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void hideKeyboard(){
        if(getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
            if(getCurrentFocus() != null){
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
    }

}
