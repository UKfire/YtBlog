package com.ytying.ytblog.act.contacts.item;

import android.content.Context;
import android.util.AttributeSet;

import com.ytying.ytblog.R;

public class Item_PaopaoTxtRight extends Item_PaopaoTxt {


    public Item_PaopaoTxtRight(Context context) {
        super(context);
    }

    public Item_PaopaoTxtRight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean headRight() {
        return true;
    }

    @Override
    protected int giveLayoutRes() {
        return R.layout.item_paopao_txt_right;
    }

}