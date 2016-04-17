package com.ytying.ytblog.act.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by UKfire on 16/4/15.
 */
public class WidgetFactory {
    public static TextView createTextView(Context context,String text,int color,int size)
    {
        TextView textView=new TextView(context);
        if(color==0)
            textView.setTextColor(Color.WHITE);
        else if(color==1)
            textView.setTextColor(Color.BLACK);
        else
            textView.setTextColor(color);
        if(size!=0)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setText(text);
        return textView;
    }

}
