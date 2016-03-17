package com.ytying.ytblog.activity.main.item;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.person.Act_EditInformation;

/**
 * Created by UKfire on 16/3/16.
 */
public class EditView extends FrameLayout {

    public EditView(final Context context) {
        super(context);
        inflate(getContext(), R.layout.item_editview, this);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Act_EditInformation.class);
                (context).startActivity(intent);
            }
        });
    }


}
