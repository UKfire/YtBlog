package com.ytying.ytblog.activity.tomorrow;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.ytying.ytblog.R;
import com.ytying.ytblog.YtApp;
import com.ytying.ytblog.act.contacts.InterChat;
import com.ytying.ytblog.act.contacts.MessageActivity;
import com.ytying.ytblog.constants.GSession;
import com.ytying.ytblog.event.DeleteConversationEvent;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.network.Response;
import com.ytying.ytblog.service.UserGetMagic;
import com.ytying.ytblog.service.UserGetThread;
import com.ytying.ytblog.utils.ImageLoaderUtil;

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

        UserGetMagic.callTask(user.getFunId(), new UserGetThread.UpdateUIListener() {
            @Override
            public void onSuccess(User _user) {
                user.setUser(_user);
                ((Activity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageLoaderUtil.getImageLoader(getContext()).displayImage(user.getHeadImage(), ivHead, ImageLoaderUtil.getDioRound());
                        tvName.setText(user.getName());
                    }
                });

            }

            @Override
            public void onFail(Response response) {

            }
        });

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GSession.getSession().putInterChat(new InterChat(recentContact.getContactId()));
                Intent intent = new Intent(getContext(), MessageActivity.class);
                getContext().startActivity(intent);
            }
        });

        if(showDivider)
            ivDivide.setVisibility(VISIBLE);
        else
            ivDivide.setVisibility(INVISIBLE);

        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(getContext()).setItems(new String[]{"删除会话"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NIMClient.getService(MsgService.class).deleteRecentContact(recentContact);
                        YtApp.getOtto().post(new DeleteConversationEvent());
                    }
                }).create().show();
                return false;
            }
        });

        if(recentContact.getUnreadCount() > 0){
            tvUnread.setText(recentContact.getUnreadCount() + "");
            tvUnread.setVisibility(VISIBLE);
        }else{
            tvUnread.setVisibility(INVISIBLE);
        }

        tvMsg.setText(recentContact.getContent());

        if(recentContact.getMsgStatus() == MsgStatusEnum.fail){
            ivState.setVisibility(VISIBLE);
        }else{
            ivState.setVisibility(GONE);
        }
    }


}
