package com.ytying.ytblog.act.contacts.item;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.ytying.ytblog.R;
import com.ytying.ytblog.YtApp;
import com.ytying.ytblog.event.ResendMessageEvent;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.network.Response;
import com.ytying.ytblog.service.UserGetMagic;
import com.ytying.ytblog.service.UserGetThread;
import com.ytying.ytblog.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by UKfire on 16/4/17.
 */
public abstract class Item_Paopao extends Item_PaopaoBase {

    protected ImageView ivHead;
    protected ImageView ivState;

    protected AnimationDrawable sendingAnimation;

    public Item_Paopao(Context context) {
        super(context);
    }

    public Item_Paopao(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        super.init();
        ivHead = (ImageView) findViewById(R.id.ivHead);
        ivState = (ImageView) findViewById(R.id.ivState);
    }

    public void updateUI(List<IMMessage> msgList, final int position) {
        super.updateUI(msgList, position);
        //发送状态的处理
        if (getMessage().getDirect() == MsgDirectionEnum.Out) {
            NIMClient.getService(MsgServiceObserve.class).observeMsgStatus(new Observer<IMMessage>() {
                @Override
                public void onEvent(IMMessage imMessage) {
                    switch (imMessage.getStatus()) {
                        case success:
                            showSucc();
                            break;
                        case fail:
                            showRetry();
                            break;
                        case sending:
                            showSending();
                            break;
                        default:
                    }
                }
            }, true);
        }

        //头像/姓名/身份的处理
        UserGetMagic.callTask(getMessage().getFromAccount(), new UserGetThread.UpdateUIListener() {
            @Override
            public void onSuccess(final User user) {
                ((Activity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivHead.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO
                            }
                        });
                        ImageLoaderUtil.getImageLoader(getContext()).displayImage(user.getHeadImage(), ivHead, ImageLoaderUtil.getDioRound());
                }
            }

            );
        }

        @Override
        public void onFail (Response response){

        }
    }

    );
}

    public void showSucc() {
        if (sendingAnimation != null)
            sendingAnimation.stop();
        ivState.setImageDrawable(null);
        ivState.setBackgroundColor(Color.TRANSPARENT);
        ivState.setVisibility(View.GONE);
    }

    public void showRetry() {
        if (sendingAnimation != null)
            sendingAnimation.stop();
        ivState.setImageDrawable(null);
        ivState.setBackgroundResource(R.drawable.selector_chatroom_paopao_retry);
        ivState.setVisibility(View.VISIBLE);
        ivState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YtApp.getOtto().post(new ResendMessageEvent(position));
            }
        });
    }

    public void showSending() {
        ivState.setImageResource(R.drawable.amls_chatroom_paopao_sending);
        ivState.setBackgroundColor(Color.TRANSPARENT);
        sendingAnimation = (AnimationDrawable) ivState.getDrawable();
        sendingAnimation.start();
        ivState.setVisibility(View.VISIBLE);
    }

    public abstract boolean headRight();

}
