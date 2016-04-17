package com.ytying.ytblog.component.chatbox;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.utils.DoUtil;
import com.ytying.ytblog.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by UKfire on 16/4/16.
 */
public class NewChatBox_Photo_Item extends FrameLayout {

    private ImageView image;
    public CheckBox check;

    private List<String> list;

    public void setList(List<String> list) {
        this.list = list;
    }

    public NewChatBox_Photo_Item(Context context, String path) {
        super(context);
        initUI();
        updateUI(path);
    }

    private void initUI() {
        inflate(getContext(), R.layout.item_newchatbox_photo, this);
        image = (ImageView) findViewById(R.id.photo_imageview);
        check = (CheckBox) findViewById(R.id.photo_checkbox);
    }

    public void updateUI(final String path) {
        ImageLoaderUtil.getImageLoader(getContext()).displayImage(path, image, ImageLoaderUtil.getDioSquareSmall());
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.isChecked() == false) {
                    check.setChecked(true);
                    DoUtil.addAnimation(check);
                    list.add(path);
                    ImagePanel.sendEnabled(1);
                } else {
                    check.setChecked(false);
                    DoUtil.addAnimation(check);
                    for (String s : list) {
                        if (s.equals(path)) {
                            list.remove(path);
                            ImagePanel.sendEnabled(2);
                            break;
                        }
                    }
                }
            }
        });
    }


}