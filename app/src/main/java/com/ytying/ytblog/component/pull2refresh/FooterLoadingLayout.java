package com.ytying.ytblog.component.pull2refresh;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytying.ytblog.R;


/**
 * 这个类封装了下拉刷新的布局
 *
 * @author Li Hong
 * @since 2013-7-30
 */
public class FooterLoadingLayout extends LoadingLayout {
    /**
     * 进度条
     */
    private ImageView ivLoading;
    private TextView tvHint;

    /**
     * 构造方法
     *
     * @param context context
     */
    public FooterLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    /**
     * 构造方法
     *
     * @param context context
     * @param attrs   attrs
     */
    public FooterLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context context
     */
    private void init(Context context) {
        ivLoading = (ImageView) findViewById(R.id.ivLoading);
        tvHint = (TextView) findViewById(R.id.tvHint);
        setState(State.RESET);
    }

    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.pull_to_load_footer, null);
        return container;
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {
    }

    @Override
    public int getContentSize() {
        View view = findViewById(R.id.pull_to_load_footer_content);
        if (null != view) {
            return view.getHeight();
        }

        return (int) (getResources().getDisplayMetrics().density * 40);
    }

    @Override
    protected void onStateChanged(State curState, State oldState) {
        ivLoading.setVisibility(View.GONE);
        tvHint.setVisibility(GONE);
        super.onStateChanged(curState, oldState);
    }

    @Override
    protected void onReset() {
    }

    @Override
    protected void onPullToRefresh() {
        ivLoading.setVisibility(View.VISIBLE);
        tvHint.setVisibility(VISIBLE);
    }

    @Override
    protected void onReleaseToRefresh() {
        ivLoading.setVisibility(View.VISIBLE);
        tvHint.setVisibility(VISIBLE);
    }

    private AnimationDrawable animationDrawable;

    @Override
    protected void onRefreshing() {
        ivLoading.setImageResource(R.drawable.footer_loading);
        animationDrawable = (AnimationDrawable) ivLoading.getDrawable();
        animationDrawable.start();

        tvHint.setVisibility(View.GONE);
        ivLoading.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onNoMoreData() {
        Log.v("d" + getmNoMoreDataHint(), "xxxx2v");
        ivLoading.setVisibility(View.GONE);
        tvHint.setVisibility(View.VISIBLE);
        tvHint.setText(getmNoMoreDataHint());

    }

}
