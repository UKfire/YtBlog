package com.ytying.ytblog.act.widget;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by UKfire on 16/3/10.
 */
public class ProgressOperatableImageView extends RelativeLayout {

    public PhotoView mImageView;
    public CircleProgress mCircleProgress;

    public ProgressOperatableImageView(Context context, int progressSize) {
        super(context);
        mImageView = new PhotoView(getContext());
        RelativeLayout.LayoutParams lpImage = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mImageView, lpImage);

        mCircleProgress = new CircleProgress(getContext());
        RelativeLayout.LayoutParams lpProgress = new LayoutParams(progressSize, progressSize);
        lpProgress.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(mCircleProgress,lpProgress);

    }

    public void setImageViewClickListener(OnClickListener listener){
        mImageView.setOnClickListener(listener);
    }

}
