package com.ytying.ytblog.act.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.utils.StringUtil;

/**
 * Created by ukfire on 15-11-20.
 */
public class StandardProgressDialog extends Dialog {

    TextView textView;

    public StandardProgressDialog(Context context){
        super(context, R.style.StandardDialog);
        setContentView(R.layout.widget_standard_progress_dialog);
        textView = (TextView) findViewById(R.id.textview);
    }

    public StandardProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setMessage(String text){
        if(!StringUtil.isBlank(text)){
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
    }
}
