package com.ytying.ytblog.activity.today.item;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytying.ytblog.MyUser;
import com.ytying.ytblog.R;
import com.ytying.ytblog.act.contacts.InterChat;
import com.ytying.ytblog.act.contacts.MessageActivity;
import com.ytying.ytblog.act.person.personpage.Act_PersonPage;
import com.ytying.ytblog.constants.GSession;
import com.ytying.ytblog.model.Blog;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.utils.ImageLoaderUtil;
import com.ytying.ytblog.utils.StringUtil;

/**
 * Created by UKfire on 16/3/15.
 */
public class Item_Design extends FrameLayout {

    private ImageView bigImage;
    private TextView  content;

    public Item_Design(Context context) {
        super(context);
        inflate(getContext(), R.layout.item_design, this);
        bigImage = (ImageView) findViewById(R.id.design_bigImage);
        content = (TextView) findViewById(R.id.design_content);

    }

    public void updateUI(final Blog blog) {

        final User user = new User();
        user.setFunId(blog.getFunId());

        if (!blog.getImage().equals("")) {
            ImageLoaderUtil.getImageLoader(getContext()).displayImage(blog.getImage(), bigImage, ImageLoaderUtil.getDioSquare());
        } else {

        }

        content.setText(StringUtil.stringToSpannableString(blog.getContent(), getContext()));

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blog.getFunId().equals(MyUser.loadUid())) {
                    Intent intent = Act_PersonPage.createIntent(getContext(), user);
                    getContext().startActivity(intent);
                } else {
                    GSession.getSession().putInterChat(new InterChat(blog.getFunId()));
                    getContext().startActivity(new Intent(getContext(), MessageActivity.class));
                }

            }
        });
    }

}
