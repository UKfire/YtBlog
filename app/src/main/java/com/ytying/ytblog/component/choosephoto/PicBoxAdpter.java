package com.ytying.ytblog.component.choosephoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.ChoosedPicBox;
import com.ytying.ytblog.utils.DimenUtil;
import com.ytying.ytblog.utils.ImageLoaderUtil;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class PicBoxAdpter extends BaseAdapter {
    protected int mScrnWidth;

    private ArrayList<String> pathList;
    private ArrayList<String> tempList;
    Context mContext;
    int duck = 2;

    /**
     * @param context
     * @param list
     * @param duck    1 means Act_Write,2 means FormApplication
     */
    public PicBoxAdpter(Context context, ArrayList<String> list, int duck) {
        pathList = list;
        tempList = new ArrayList<>();
        for(String s : list){
            tempList.add("file://" + s);
        }
        this.mContext = context;
        mScrnWidth = DimenUtil.getWidth();
        this.duck = duck;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LinearLayout ll = new LinearLayout(mContext);
        ImageView ivImage = new ImageView(mContext);
        if (getItem(position) != null) {

            ll.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent it = new Intent(mContext, Act_PicPreview.class);
                    Bundle b = new Bundle();
                    b.putStringArrayList("pathList", pathList);
                    it.putExtra("bundle", b);
                    it.putExtra("order", position);
                    ((Activity) mContext).startActivityForResult(it, ChoosedPicBox.PIC_PREVIEW);
                }
            });

            ImageLoaderUtil.getImageLoader(mContext).displayImage(tempList.get(position), ivImage, ImageLoaderUtil.getDioSquareSmall());
            ivImage.setTag(position);

        } else {

            ivImage.setTag(position);
            if (duck == 1) {
                ivImage.setImageResource(R.drawable.btn_addpic);
            } else if (duck == 2) {
                ivImage.setImageResource(R.mipmap.form_bt_uploadpic);
            }
            ivImage.setScaleType(ScaleType.CENTER_CROP);

            ll.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                    intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, pathList);
                    ((Activity) mContext).startActivityForResult(intent, ChoosedPicBox.CHOOSE_PHOTO);

                }
            });

        }
        ivImage.setScaleType(ScaleType.CENTER_CROP);
        if (duck == 1) {
            ll.addView(ivImage, (mScrnWidth - dp2px(32) - dp2px(20)) / 5, (mScrnWidth - dp2px(32) - dp2px(20)) / 5);
        } else if (duck == 2) {
            ll.addView(ivImage, dp2px(74), dp2px(74));
        }
        return ll;

    }

    public int dp2px(int dp) {
        return DimenUtil.dp2px(dp);
    }

    public int getCount() {
        return pathList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position < pathList.size())
            return pathList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
