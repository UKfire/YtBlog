package com.ytying.ytblog.activity.today;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ytying.ytblog.activity.today.item.Item_Design;
import com.ytying.ytblog.model.Design;

import java.util.List;

/**
 * Created by UKfire on 16/3/12.
 */
public class TodayAdapter extends BaseAdapter {

    Context context;
    List<Design> list;

    public TodayAdapter(Context context, List<Design> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        if (convertView == null && !(convertView instanceof Item_Design)) {
            Item_Design item = new Item_Design(context);
            convertView = item;
        }
        ((Item_Design) convertView).updateUI(list.get(position));
        return convertView;
    }
}
