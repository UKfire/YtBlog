package com.ytying.ytblog.activity.tomorrow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.netease.nimlib.sdk.msg.model.RecentContact;

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
        return messages.size();
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
        return null;
    }
}
