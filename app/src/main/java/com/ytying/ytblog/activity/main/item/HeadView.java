package com.ytying.ytblog.activity.main.item;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.person.Act_PersonPage;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.utils.ImageLoaderUtil;

/**
 * Created by UKfire on 16/3/13.
 */
public class HeadView extends FrameLayout {

    private TextView name;
    private TextView motto;
    private ImageView head, background, sex;

    public HeadView(Context context) {
        super(context);
        inflate(getContext(), R.layout.item_headview, this);
        name = (TextView) findViewById(R.id.name);
        head = (ImageView) findViewById(R.id.headview);
        sex = (ImageView) findViewById(R.id.sex);
        motto = (TextView) findViewById(R.id.motto);
        background = (ImageView) findViewById(R.id.backgroundImage);
    }

    public void updateUI(final User user) {
        name.setText(user.getName());
        ImageLoaderUtil.getImageLoader(getContext()).displayImage(user.getHeadImage(), head, ImageLoaderUtil.getDioRound());
        ImageLoaderUtil.getImageLoader(getContext()).displayImage(user.getBackImage(), background, ImageLoaderUtil.getDioSquare());
        head.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Act_PersonPage.createIntent(getContext(), user);
                getContext().startActivity(intent);
            }
        });

    }

}
