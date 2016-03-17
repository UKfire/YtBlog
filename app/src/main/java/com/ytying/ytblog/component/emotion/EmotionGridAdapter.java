package com.ytying.ytblog.component.emotion;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ytying.ytblog.utils.DimenUtil;

import java.util.List;


/**
 * Created by urmyc on 15/7/24.
 */

public class EmotionGridAdapter extends BaseAdapter {
    private final int mScrnWidth;
    ;

    private final int LINE_PER_PAGE = 4;
    private final int COUNT_PER_LINE = 6;

    private List<Smile> ebList;
    Context context;

    ViewPager vp;

    public EmotionGridAdapter(Context context, List<Smile> ebList) {
        mScrnWidth = DimenUtil.getWidth();
        this.ebList = ebList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            ImageView ivEmotion = new ImageView(context);
            ivEmotion.setImageResource(ebList.get(position).getResId());
            LinearLayout layout = new LinearLayout(context);
            layout.setGravity(Gravity.CENTER);
            layout.setTag(ebList.get(position));
            layout.addView(ivEmotion, DimenUtil.dp2px(28), DimenUtil.dp2px(28 + 20));
            return layout;

    }

    public int getCount() {
        return ebList.size();
    }

    @Override
    public Object getItem(int position) {
        return ebList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
