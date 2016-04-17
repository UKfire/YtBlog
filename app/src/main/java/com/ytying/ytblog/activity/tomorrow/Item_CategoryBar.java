package com.ytying.ytblog.activity.tomorrow;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.WidgetFactory;
import com.ytying.ytblog.utils.DimenUtil;


public class Item_CategoryBar extends FrameLayout {

    public Item_CategoryBar(Context context) {
        super(context);
        init();
    }

    public Item_CategoryBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void init() {

    }

    public void updateUI(String text, int height) {
        this.removeAllViews();
        TextView textView = WidgetFactory.createTextView(getContext(), text, getResources().getColor(R.color.gray_four), 11);
        textView.setPadding(DimenUtil.dp2px(16),0,0,0);
        this.addView(textView, LayoutParams.MATCH_PARENT, height);
    }

    public void updateUI(String text) {
        this.updateUI(text,DimenUtil.dp2px(24));
    }

}
