package com.ytying.ytblog.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.utils.DimenUtil;

/**
 * Created by UKfire on 16/3/10.
 */
public abstract class Act_Common_Linear extends BaseActivity {

    protected int mWidth;
    protected int mHeight;

    protected LinearLayout mParent;

    protected ActionBarLayout mActionbar;

    @Override
    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        mWidth = DimenUtil.getWidth();
        mHeight = DimenUtil.getHeight();

        initData();

        mParent = new LinearLayout(this);
        setContentView(mParent);
        mParent.setBackgroundResource(R.color.gray_six);
        mParent.setOrientation(LinearLayout.VERTICAL);

        mActionbar = new ActionBarLayout(this);
        mParent.addView(mActionbar, ViewGroup.LayoutParams.MATCH_PARENT, ActionBarLayout.TOPBAR_HEIGHT);
    }

    public ActionBarLayout getActionBarM(){
        return mActionbar;
    }
}
