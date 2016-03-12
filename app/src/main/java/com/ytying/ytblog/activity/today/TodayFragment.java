package com.ytying.ytblog.activity.today;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.act.widget.KeListView;
import com.ytying.ytblog.base.BaseFragment;

/**
 * Created by UKfire on 15/11/22.
 */
public class TodayFragment extends BaseFragment implements IView {

    Presenter presenter;
    ActionBarLayout actionbar;
    KeListView listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new Presenter(this);
        View v = inflater.inflate(R.layout.fgm_today, container, false);
        actionbar = (ActionBarLayout) v.findViewById(R.id.today_actionbar);
        listview = (KeListView) v.findViewById(R.id.today_listview);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionbar.setTitle("慧装修");

        presenter.initList();

        final TodayAdapter adapter = new TodayAdapter(getActivity(), presenter.getList());

        listview.setAdapter(adapter);

        listview.setOnRefreshListener(new KeListView.RefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.runRefresh();
                        adapter.notifyDataSetChanged();
                        listview.onRefreshComplete();
                    }
                }, 2000);

            }

            @Override
            public void onLoad() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.runLoad();
                        adapter.notifyDataSetChanged();
                        listview.onLoadComplete();
                    }
                }, 2000);
            }
        });
    }
}
