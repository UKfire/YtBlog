package com.ytying.ytblog.act.contacts.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.ytying.ytblog.R;
import com.ytying.ytblog.utils.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by UKfire on 16/4/17.
 */
public abstract  class Item_PaopaoBase extends FrameLayout {

    protected ImageView ivEmpty;
    protected TextView tvTimeStamp;
    protected ViewGroup paopaoLayout;

    protected int position;
    protected List<IMMessage> msgList;

    public Item_PaopaoBase(Context context) {
        super(context);
        init();
    }

    public Item_PaopaoBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        inflate(getContext(), giveLayoutRes(), this);
        ivEmpty = (ImageView) findViewById(R.id.ivEmpty);
        tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        paopaoLayout = (ViewGroup) findViewById(R.id.paopaoLayout);
    }

    public void updateUI(List<IMMessage> msgList, final int position) {

        //拿到数据
        this.position = position;
        this.msgList = msgList;

        //显示时间
        initViewTime();

        //设置点击事件
        paopaoLayout.setOnClickListener(givePaoPaoClickListener(getMessage()));

        //设置长按事件
        paopaoLayout.setOnLongClickListener(givePaoPaoLongClickListener(getMessage()));

    }

    private void initViewTime() {
        if (position == 0) {
            ivEmpty.setVisibility(View.VISIBLE);
            tvTimeStamp.setText(DateUtil.getTimestampString(getMessage().getTime()));
            tvTimeStamp.setVisibility(View.VISIBLE);
        } else {
            ivEmpty.setVisibility(View.GONE);
            // 两条消息时间离得如果稍长，显示时间
            IMMessage prevMessage = getPrevMessage();
            if (prevMessage != null && DateUtil.isCloseEnough(getMessage().getTime(), prevMessage.getTime())) {
                tvTimeStamp.setVisibility(View.GONE);
            } else {
                tvTimeStamp.setText(DateUtil.getTimestampString(getMessage().getTime()));
                tvTimeStamp.setVisibility(View.VISIBLE);
            }
        }
    }

    protected IMMessage getMessage() {
        try {
            return msgList.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected IMMessage getPrevMessage() {
        try {
            return msgList.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected int getPosition() {
        return position;
    }

    protected abstract int giveLayoutRes();

    protected abstract OnClickListener givePaoPaoClickListener(final IMMessage msg);

    protected abstract OnLongClickListener givePaoPaoLongClickListener(final IMMessage msg);

}
