package com.ytying.ytblog.act.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.utils.DimenUtil;
import com.ytying.ytblog.utils.ImageLoaderUtil;
import com.ytying.ytblog.utils.StringUtil;

import java.util.ArrayList;


public class ActionBarLayout extends FrameLayout {

    public static int TOPBAR_HEIGHT = DimenUtil.dp2px(48);
    private int padding = getResources().getDimensionPixelSize(R.dimen.dp_unit) * 13;

    private TextView tvTitle;
    private TextView tvTitle2;
    private ProgressBar progressBar;

    private LinearLayout operateLayout, operateLeftLayout;
    private ArrayList<View> leftOperateBtnList;
    private ArrayList<View> operateBtnList;

    private LinearLayout stepLayout;
    private ImageView ivBackground;
    private ImageView ivgo;
    private TextView tvRight, tvLeft;


    public ActionBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ActionBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ActionBarLayout(Context context) {
        super(context);
        init();
    }

    public void init() {
        this.setBackgroundResId(R.color.actionbarColor);
        inflate(getContext(), R.layout.widget_actionbar, this);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle2 = (TextView) findViewById(R.id.tvTitle2);
        ivgo = (ImageView) findViewById(R.id.ivgo);
        tvRight = (TextView) findViewById(R.id.tvRight);
        tvLeft = (TextView) findViewById(R.id.tvLeft);
        stepLayout = (LinearLayout) findViewById(R.id.stepLayout);
        operateLayout = (LinearLayout) findViewById(R.id.operateLayout);
        operateLeftLayout = (LinearLayout) findViewById(R.id.operateLeftLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        operateBtnList = new ArrayList<View>();
        leftOperateBtnList = new ArrayList<View>();
        ivBackground = (ImageView) findViewById(R.id.ivBackground);
    }

    public void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            setTitle("");
            setTitle2("");
        } else
            progressBar.setVisibility(View.GONE);
    }


    public void setHomeBtnAsBack(final Activity act) {
        addOperateButton(R.mipmap.icon_topbar_xiaopang_back, new OnClickListener() {

            @Override
            public void onClick(View v) {
                act.onBackPressed();
                act.setResult(Activity.RESULT_CANCELED);
                act.finish();
            }
        }, true);
    }


    public void setTitle(String tittle) {

        if (StringUtil.isBlank(tittle)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(tittle);
        }
    }

    public void setTitle2(String tittle) {
        if (StringUtil.isBlank(tittle)) {
            tvTitle2.setVisibility(View.GONE);
        } else {
            tvTitle2.setVisibility(View.VISIBLE);
            tvTitle2.setText(tittle);
        }
    }


    public ImageButton addOperateButton(int drawableResId, OnClickListener listener, boolean left) {
        ImageButton ibtn = new ImageButton(getContext());
        ibtn.setBackgroundResource(R.drawable.selector_transparent_13pctblack);
        ibtn.setPadding(padding, padding, padding, padding);
        ibtn.setScaleType(ScaleType.FIT_XY);
        ibtn.setImageResource(drawableResId);
        addOperateButton(ibtn, listener, left);
        return ibtn;
    }

    public void addTextButton(String text, OnClickListener listener, boolean left) {
        Button btn = new Button(getContext());
        btn.setBackgroundResource(R.drawable.selector_transparent_13pctblack);
        btn.setGravity(Gravity.CENTER);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        btn.setTextColor(getResources().getColor(R.color.white));
        btn.setText(text);
        addOperateButton(btn, listener, left);

    }

    public void addOperateButton(View view, OnClickListener listener, boolean left) {
        view.setOnClickListener(listener);
        addOperateButton(view, left);
    }

    public void addOperateButton(View view, boolean left) {
        if (left) {
            leftOperateBtnList.add(view);
            operateLeftLayout.addView(view, TOPBAR_HEIGHT, TOPBAR_HEIGHT);
        } else {
            operateBtnList.add(view);
            operateLayout.addView(view, TOPBAR_HEIGHT, TOPBAR_HEIGHT);
        }
    }

    public void removeOperateButton() {
        operateLayout.removeAllViews();
    }


    public void showIvGo() {
        ivgo.setVisibility(VISIBLE);
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }


    public ArrayList<View> getOperateButtonList() {
        return operateBtnList;
    }

    public ArrayList<View> getLeftOperateButtonList() {
        return leftOperateBtnList;
    }

    public TextView getTvTitle() {
        return this.tvTitle;
    }

    public void setBackgroundResId(int resId) {
        this.setBackgroundResource(resId);
    }

    public void setOnlineBackground(String s) {
        ImageLoaderUtil.getImageLoader(getContext()).displayImage(s, ivBackground);
    }
}
