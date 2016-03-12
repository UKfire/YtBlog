package com.ytying.ytblog.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
}
