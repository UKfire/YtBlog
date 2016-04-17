package com.ytying.ytblog.act.contacts;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.ytying.ytblog.model.domin.User;

import java.util.List;

/**
 * Created by UKfire on 16/4/16.
 */
public class PaopaoAdapter extends BaseAdapter {

    private User user;
    private Context context;
    private List<IMMessage> msgList;

    public PaopaoAdapter(Context context,List<IMMessage> msgList,User user){
        this.user = user;
        this.context = context;
        this.msgList = msgList;
    }

    @Override
    public int getCount() {
        return msgList == null ? 0 : msgList.size();
    }

    @Override
    public IMMessage getItem(int position) {
        if(msgList != null && position < msgList.size())
            return msgList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final IMMessage message = getItem(position);
        MsgTypeEnum type = message.getMsgType();

        if(type == MsgTypeEnum.text){
            //右边,我发出去的
            if(message.getDirect() == MsgDirectionEnum.Out){
                TextView item  = new TextView(context);
                item.setText(message.getContent());
                item.setTextColor(Color.BLUE);
                convertView = item;
            }
            //左边,我收到的
            else if(message.getDirect() == MsgDirectionEnum.In){
                TextView item  = new TextView(context);
                item.setText(message.getContent());
                convertView = item;
            }
        }else if(type == MsgTypeEnum.audio){

        }else if(type == MsgTypeEnum.image){

        }
        return convertView == null ? new LinearLayout(context) : convertView;
    }
}
