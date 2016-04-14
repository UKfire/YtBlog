package com.ytying.ytblog.activity.tomorrow;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.ytying.ytblog.R;
import com.ytying.ytblog.model.domin.User;

/**
 * Created by UKfire on 16/4/14.
 */
public class Item_Conversation extends FrameLayout {

    private ImageView ivHead;
    private TextView tvName;
    private ImageView ivState;
    private TextView tvMsg;
    private TextView tvTime;
    private TextView tvUnread;
    private ImageView ivDivide;

    private User user = new User();

    public Item_Conversation(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_conversation,this);
        ivHead = (ImageView) findViewById(R.id.ivHead);
        tvName = (TextView) findViewById(R.id.tvName);
        ivState = (ImageView) findViewById(R.id.ivState);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvUnread = (TextView) findViewById(R.id.tvUnread);
        ivDivide = (ImageView) findViewById(R.id.ivDivide);
    }

    public void updateUI(final RecentContact recentContact,boolean showDivider){
        tvName.setTextColor(getResources().getColor(R.color.gray_one));
        user.setFunId(recentContact.getContactId());


    }


}
