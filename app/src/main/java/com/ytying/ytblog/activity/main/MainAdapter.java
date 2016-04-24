package com.ytying.ytblog.activity.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ytying.ytblog.MyUser;
import com.ytying.ytblog.activity.main.item.EditView;
import com.ytying.ytblog.activity.main.item.HeadView;

/**
 * Created by UKfire on 16/3/13.
 */
public class MainAdapter extends BaseAdapter {

    Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
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
        if (position == 0) {
            HeadView view = new HeadView(context);
            view.updateUI(MyUser.getSelf());
            convertView = view;
        } else if (position == 1) {
            EditView view = new EditView(context);
            convertView = view;
        }
        return convertView;
    }
}
