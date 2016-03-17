package com.ytying.ytblog.activity.today.item;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.model.Design;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.utils.ImageLoaderUtil;

/**
 * Created by UKfire on 16/3/15.
 */
public class Item_Design extends FrameLayout {

    private ImageView bigImage, headView, sex, ivLike, ivZan;
    private TextView name, city;

    public Item_Design(Context context) {
        super(context);
        inflate(getContext(), R.layout.item_design, this);
        bigImage = (ImageView) findViewById(R.id.design_bigImage);
        headView = (ImageView) findViewById(R.id.design_headview);
        sex = (ImageView) findViewById(R.id.design_sex);
        ivLike = (ImageView) findViewById(R.id.ivLike);
        ivZan = (ImageView) findViewById(R.id.ivZan);
        name = (TextView) findViewById(R.id.design_name);
        city = (TextView) findViewById(R.id.design_city);

        ivZan.setOnClickListener(onZanClickListner);
    }

    public void updateUI(Design design) {
        User user = design.getUser();
        if (design.getDesignImage().size() > 0) {
            ImageLoaderUtil.getImageLoader(getContext()).displayImage(design.getDesignImage().get(0), bigImage, ImageLoaderUtil.getDioSquare());
        } else {
            bigImage.setImageResource(R.color.gray_four);
        }
        ImageLoaderUtil.getImageLoader(getContext()).displayImage(user.getHeadImage(), headView, ImageLoaderUtil.getDioOtherRound());
        if (user.getSex().equals("male"))
            sex.setImageResource(R.mipmap.new_icon_boy);
        else
            sex.setImageResource(R.mipmap.new_icon_girl);
        name.setText(user.getNickname());
        if (user.getCity().equals(""))
            city.setText("所在城市：未知");
        else
            city.setText("所在城市：" + user.getCity());
    }


    OnClickListener onZanClickListner = new OnClickListener() {

        @Override
        public void onClick(View v) {
            HuiAnimation.playAnimation(ivLike, ivZan);
            ivZan.setImageResource(R.mipmap.square_icon_like_pressed);

        }
    };

}
