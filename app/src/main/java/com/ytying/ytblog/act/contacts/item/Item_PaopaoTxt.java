package com.ytying.ytblog.act.contacts.item;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.ytying.ytblog.R;
import com.ytying.ytblog.utils.DoUtil;
import com.ytying.ytblog.utils.StringUtil;

import java.util.List;

/**
 * Created by UKfire on 16/4/17.
 */
public abstract class Item_PaopaoTxt extends Item_Paopao {
    protected TextView tvContent;


    public Item_PaopaoTxt(Context context) {
        super(context);
    }

    public Item_PaopaoTxt(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        super.init();
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    public void updateUI(List<IMMessage> msgList, int position) {
        super.updateUI(msgList, position);
        String content = getMessage().getContent();
        SpannableString span = StringUtil.stringToSpannableString(content, getContext());
        tvContent.setText(span, TextView.BufferType.SPANNABLE);
    }

    @Override
    protected OnClickListener givePaoPaoClickListener(IMMessage msg) {
        return null;
    }

    @Override
    protected OnLongClickListener givePaoPaoLongClickListener(IMMessage msg) {
        return new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String paste = StringUtil.removeTextTag(tvContent.getText().toString());
                ClipboardManager cmb = (ClipboardManager) tvContent.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(paste);
                DoUtil.showToast(tvContent.getContext(), "文字已经复制到剪贴板");
                return true;
            }
        };
    }

}
