package com.ytying.ytblog.activity.main.item;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.ytying.ytblog.R;

/**
 * Created by UKfire on 16/3/16.
 */
public class FocusView extends FrameLayout {

    public FocusView(Context context) {
        super(context);
        inflate(getContext(), R.layout.item_focusview, this);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
