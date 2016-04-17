package com.ytying.ytblog.component.chatbox;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by UKfire on 16/4/16.
 */
public class SimpleTextMatcher {
    public static TextWatcher create(final  TextChange textChange){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChange.onTextChange(s,count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    public interface TextChange{
        void onTextChange(CharSequence s, int count);
    }

}
