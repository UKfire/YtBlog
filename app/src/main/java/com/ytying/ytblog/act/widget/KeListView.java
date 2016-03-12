package com.ytying.ytblog.act.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.ytying.ytblog.R;

/**
 * Created by UKfire on 16/3/12.
 */

public class KeListView extends ListView implements AbsListView.OnScrollListener {

    View header;
    int headerHeight;
    int firstVisibleItem;
    int scrollState;
    boolean isRemark;
    int startY;

    int state;
    final int NONE = 0; //正常状态
    final int PULL = 1; //下拉状态
    final int RELEASE = 2;  //即将释放状态
    final int REFRESHING = 3;   //刷新状态　

    View footer;
    int totalItemCount;
    int lastVisibleItem;
    boolean isLoading;

    RefreshListener listener;

    public KeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public KeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public KeListView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.layout_header, null);
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        topPadding(-headerHeight);
        this.addHeaderView(header);

        footer = inflater.inflate(R.layout.layout_footer, null);
        footer.findViewById(R.id.load_layout).setVisibility(GONE);
        this.addFooterView(footer);

        this.setOnScrollListener(this);
    }

    private void measureView(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null)
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int width = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int height;
        int tempHeight = lp.height;
        if (tempHeight > 0)
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        else
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
    }

    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        if (totalItemCount == lastVisibleItem && scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                footer.findViewById(R.id.load_layout).setVisibility(VISIBLE);
                listener.onLoad();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELEASE) {
                    state = REFRESHING;
                    refreshViewByState();
                    listener.onRefresh();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    refreshViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void onMove(MotionEvent ev) {
        if (!isRemark)
            return;
        int tempY = (int) ev.getY();
        int space = tempY - startY;
        int topPadding = space - headerHeight;
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    refreshViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > headerHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELEASE;
                    refreshViewByState();
                }
                break;
            case RELEASE:
                topPadding(topPadding);
                if (space < headerHeight + 30) {
                    state = NONE;
                    refreshViewByState();
                } else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                    refreshViewByState();
                }
                break;
        }
    }


    private void refreshViewByState() {
        switch (state) {
            case NONE:
                topPadding(-headerHeight);
                break;
            case PULL:
                break;
            case RELEASE:
                break;
            case REFRESHING:
                topPadding(58);
                break;
        }
    }

    public void onRefreshComplete() {
        state = NONE;
        isRemark = false;
        refreshViewByState();
    }

    public void onLoadComplete() {
        isLoading = false;
        footer.findViewById(R.id.load_layout).setVisibility(GONE);
    }

    public void setOnRefreshListener(RefreshListener listener) {
        this.listener = listener;
    }

    public interface RefreshListener {
        void onRefresh();

        void onLoad();
    }

}
