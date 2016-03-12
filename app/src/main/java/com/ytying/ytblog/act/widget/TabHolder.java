package com.ytying.ytblog.act.widget;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by UKfire on 15/11/23.
 */
public class TabHolder {

    private RelativeLayout tabLayout;
    private ImageView btIcon;
    private TextView indicator;
    private ImageView redot;

    public TabHolder(RelativeLayout layout) {
        tabLayout = layout;
        btIcon = (ImageView) layout.getChildAt(0);
        indicator = (TextView) layout.getChildAt(1);
        redot = (ImageView) layout.getChildAt(2);
    }

    public void showRedot(boolean showOrNot) {
        if (showOrNot) {
            redot.setVisibility(View.VISIBLE);
        } else {
            redot.setVisibility(View.GONE);
        }
    }

    public RelativeLayout getTabLayout() {
        return this.tabLayout;
    }


    public ImageView getBtIcon() {
        return btIcon;
    }

    public void setBtIcon(ImageView btIcon) {
        this.btIcon = btIcon;
    }

    public TextView getIndicator() {
        return indicator;
    }

    public void setIndicator(TextView indicator) {
        this.indicator = indicator;
    }

    public void setTabLayout(RelativeLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public ImageView getRedot() {
        return redot;
    }

    public void setRedot(ImageView redot) {
        this.redot = redot;
    }
}





