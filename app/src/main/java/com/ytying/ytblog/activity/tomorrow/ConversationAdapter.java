package com.ytying.ytblog.activity.tomorrow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.ytying.ytblog.utils.DimenUtil;

import java.util.List;

/**
 * Created by UKfire on 16/4/14.
 */
public class ConversationAdapter extends BaseAdapter {

    Context context;
    List<RecentContact> messages;

    public ConversationAdapter(Context context,List<RecentContact> messages){
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position == 0){
            Item_MyFocus item = new Item_MyFocus(context);
            convertView = item;
        }else if(position == 1){
            Item_CategoryBar item = new Item_CategoryBar(context);
            item.updateUI("最近聊天", DimenUtil.dp2px(24));
            convertView = item;
        }else{
            RecentContact recentContact = messages.get(position - 2);
            if (convertView == null || !(convertView instanceof Item_Conversation)) {
                Item_Conversation item = new Item_Conversation(context);
                convertView = item;
            }
            boolean showDivider = true;
            if(position == getCount() - 1)
                showDivider = false;
            ((Item_Conversation)convertView).updateUI(recentContact,showDivider);
        }
        return convertView;
    }
}
