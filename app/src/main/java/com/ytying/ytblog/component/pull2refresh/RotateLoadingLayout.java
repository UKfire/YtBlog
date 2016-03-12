package com.ytying.ytblog.component.pull2refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ytying.ytblog.R;


/**
 * 这个类封装了下拉刷新的布局
 *
 * @author Li Hong
 * @since 2013-7-30
 */
public class RotateLoadingLayout extends LoadingLayout {
    /**
     * 旋转动画的时间
     */
    public static final int ROTATION_ANIMATION_DURATION = 2400;
    /**
     * 动画插值
     */
    public static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
    /**
     * Header的容器
     */
    private RelativeLayout mHeaderContainer;
    /**
     * 箭头图片
     */
    private ImageView mArrowImageView;
    /**
     * 状态提示TextView
     */
    private TextView mHintTextView;
    /**最后更新时间的TextView*/
//    private TextView mHeaderTimeView;
    /**最后更新时间的标题*/
//    private TextView mHeaderTimeViewTitle;
    /**
     * 旋转的动画
     */
    private Animation mRotateAnimation;

    /**
     * 构造方法
     *
     * @param context context
     */
    public RotateLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    /**
     * 构造方法
     *
     * @param context context
     * @param attrs   attrs
     */
    public RotateLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context context
     */
    private void init(Context context) {
        mHeaderContainer = (RelativeLayout) findViewById(R.id.pull_to_refresh_header_content);
        mArrowImageView = (ImageView) findViewById(R.id.pull_to_refresh_header_arrow);
        mHintTextView = (TextView) findViewById(R.id.pull_to_refresh_header_hint_textview);
//        mHeaderTimeView = (TextView) findViewById(R.id.pull_to_refresh_header_time);
//        mHeaderTimeViewTitle = (TextView) findViewById(R.id.pull_to_refresh_last_update_time_text);

//        mArrowImageView.setScaleType(ScaleType.CENTER);
//        mArrowImageView.setImageResource(R.drawable.really_rotate);

        float pivotValue = 0.5f;    // SUPPRESS CHECKSTYLE
        float toDegree = 360.0f;    // SUPPRESS CHECKSTYLE
        mRotateAnimation = new RotateAnimation(0.0f, toDegree, Animation.RELATIVE_TO_SELF, pivotValue,
                Animation.RELATIVE_TO_SELF, pivotValue);
        mRotateAnimation.setFillAfter(true);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
    }

    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.dong_pull_to_refresh_header, null);
        return container;
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {
        // 如果最后更新的时间的文本是空的话，隐藏前面的标题
//        mHeaderTimeViewTitle.setVisibility(TextUtils.isEmpty(label) ? View.INVISIBLE : View.VISIBLE);
//        mHeaderTimeView.setText(label);
    }

    @Override
    public int getContentSize() {
        if (null != mHeaderContainer) {
            return mHeaderContainer.getHeight();
        }

        return (int) (getResources().getDisplayMetrics().density * 60);
    }

    @Override
    protected void onStateChanged(State curState, State oldState) {
        super.onStateChanged(curState, oldState);
    }

    @Override
    protected void onReset() {
        resetRotation();
        mHintTextView.setText("下拉刷新");
    }

    @Override
    protected void onReleaseToRefresh() {
        mHintTextView.setText("释放刷新");
    }

    @Override
    protected void onPullToRefresh() {
        try {
            mHintTextView.setText("下拉刷新");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRefreshing() {
        resetRotation();
        mArrowImageView.startAnimation(mRotateAnimation);
        try {
            mHintTextView.setText("正在刷新");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPull(float scale) {

//    	if(getState()==State.PULL_TO_REFRESH)
//    	{
//    		mArrowImageView.setRotation(180);
//    	}
//    	else
//    	{
//    		mArrowImageView.setRotation(0);
//    	}

        float angle = scale * 180f; // SUPPRESS CHECKSTYLE
        mArrowImageView.setRotation(angle);
    }

    /**
     * 重置动画
     */
    private void resetRotation() {
        mArrowImageView.clearAnimation();
        mArrowImageView.setRotation(0);
    }
}
