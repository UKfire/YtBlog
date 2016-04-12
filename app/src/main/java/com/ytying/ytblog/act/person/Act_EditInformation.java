package com.ytying.ytblog.act.person;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ytying.ytblog.MyUser;
import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.utils.DoUtil;

/**
 * Created by UKfire on 16/3/16.
 */
public class Act_EditInformation extends Activity {

    private ActionBarLayout actionbar;
    private EditText name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editinformation);
        actionbar = (ActionBarLayout) findViewById(R.id.edit_actionbar);
        name = (EditText) findViewById(R.id.edit_nickname);
        phone = (EditText) findViewById(R.id.edit_phone);
        initUI();
        actionbar.addOperateButton(R.mipmap.nav_icon_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sex = "male";
                if(name.getText().toString().equals(""))
                    DoUtil.showToast(Act_EditInformation.this,"昵称不能为空");
                else{
                    String nickname = name.getText().toString();
                    String phoneString = phone.getText().toString();

                    //TODO wkf
                }
            }
        }, false);
    }

    private void initUI() {
        actionbar.setTitle("修改个人资料");
        User user = MyUser.getSelf();
        if (!user.getName().equals(""))
            name.setText(user.getName());
        if (!user.getPhone().equals(""))
            phone.setText(user.getPhone());
    }
}
