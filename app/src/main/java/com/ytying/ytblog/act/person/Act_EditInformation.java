package com.ytying.ytblog.act.person;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

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
    private EditText name, motto, phone, email, qq, city;
    private RadioButton male, female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editinformation);
        actionbar = (ActionBarLayout) findViewById(R.id.edit_actionbar);
        name = (EditText) findViewById(R.id.edit_nickname);
        motto = (EditText) findViewById(R.id.edit_motto);
        phone = (EditText) findViewById(R.id.edit_phone);
        email = (EditText) findViewById(R.id.edit_email);
        qq = (EditText) findViewById(R.id.edit_qq);
        city = (EditText) findViewById(R.id.edit_city);
        male = (RadioButton) findViewById(R.id.edit_male);
        female = (RadioButton) findViewById(R.id.edit_female);
        initUI();
        actionbar.addOperateButton(R.mipmap.nav_icon_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sex = "male";
                if(female.isChecked())
                    sex = "female";
                if(name.getText().toString().equals(""))
                    DoUtil.showToast(Act_EditInformation.this,"昵称不能为空");
                else{
                    String mottoString = motto.getText().toString();
                    String nickname = name.getText().toString();
                    String qqNumber = qq.getText().toString();
                    String emailString = email.getText().toString();
                    String phoneString = phone.getText().toString();
                    String cityString = city.getText().toString();

                    //TODO wkf
                }
            }
        }, false);
    }

    private void initUI() {
        actionbar.setTitle("修改个人资料");
        User user = MyUser.getSelf();
        if (!user.getNickname().equals(""))
            name.setText(user.getNickname());
        if (user.getSex().equals("male"))
            male.setChecked(true);
        else
            female.setChecked(true);
        if (!user.getMotto().equals(""))
            motto.setText(user.getMotto());
        if (!user.getPhoneNumber().equals(""))
            phone.setText(user.getPhoneNumber());
        if (!user.getQqNumber().equals(""))
            qq.setText(user.getQqNumber());
        if (!user.getEmail().equals(""))
            email.setText(user.getEmail());
        if (!user.getCity().equals(""))
            city.setText(user.getCity());
    }
}
