package com.ytying.ytblog.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by UKfire on 16/3/10.
 */
public class DoUtil {

    public static void startActivity(Context ctx, Intent intent) {
        ((Activity) ctx).startActivity(intent);
    }

    public static void finish(Context ctx) {
        ((Activity) ctx).finish();
    }

    public static void showToast(Context ctx, String toast) {
        if (toast.length() > 0)
            Toast.makeText(ctx, toast, Toast.LENGTH_SHORT).show();
    }

    public static void addAnimation(View view) {
        float[] vaules = new float[]{0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f, 1.1f, 1.2f, 1.3f, 1.25f, 1.2f, 1.15f, 1.1f, 1.0f};
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", vaules), ObjectAnimator.ofFloat(view, "scaleY", vaules));
        set.setDuration(150);
        set.start();
    }

}
