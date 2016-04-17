package com.ytying.ytblog.activity.tomorrow;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.ytying.ytblog.R;

/**
 * Created by UKfire on 16/4/15.
 */
public class Item_MyFocus extends FrameLayout {

    public Item_MyFocus(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_myfocus,this);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
