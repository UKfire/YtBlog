package com.ytying.ytblog.activity.main.item;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.ytying.ytblog.R;
import com.ytying.ytblog.YtApp;

/**
 * Created by UKfire on 16/3/16.
 */
public class ExitView extends FrameLayout {

    public ExitView(final Context context) {
        super(context);
        inflate(getContext(), R.layout.item_exitview, this);
        this.setOnClickListener(null);
        findViewById(R.id.exit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                YtApp.logout((Activity) context);
            }
        });
    }
}
