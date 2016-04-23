package com.ytying.ytblog.activity.today;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.design.Act_Write_Design;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.activity.main.MainActivity;
import com.ytying.ytblog.base.BaseFragment;
import com.ytying.ytblog.component.pull2refresh.PullToRefreshBase;
import com.ytying.ytblog.component.pull2refresh.PullToRefreshListView;

/**
 * Created by UKfire on 15/11/22.
 */
public class TodayFragment extends BaseFragment implements IView {

    Presenter presenter;
    ActionBarLayout actionbar;
    PullToRefreshListView listview;
    TodayAdapter adapter;
    String lastId = "0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new Presenter(this);
        View v = inflater.inflate(R.layout.fgm_today, container, false);
        actionbar = (ActionBarLayout) v.findViewById(R.id.today_actionbar);
        listview = (PullToRefreshListView) v.findViewById(R.id.today_listview);
        listview.getRefreshableView().setDivider(null);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionbar.setTitle("光影安大");
        actionbar.addOperateButton(R.mipmap.nav_icon_menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mDrawerLayout.isDrawerOpen(MainActivity.mDrawerList)) {
                    MainActivity.mDrawerLayout.closeDrawer(MainActivity.mDrawerList);
                } else {
                    MainActivity.mDrawerLayout.openDrawer(MainActivity.mDrawerList);
                }
            }
        }, true);

        actionbar.addOperateButton(R.mipmap.nav_icon_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Act_Write_Design.createIntent(getActivity());
                getActivity().startActivity(intent);
            }
        }, false);

        presenter.initList();

        adapter = new TodayAdapter(getActivity(), presenter.getList());

        listview.getRefreshableView().setAdapter(adapter);

        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.runRefresh("0");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.runRefresh(lastId);
            }
        });

        listview.doPullRefreshing(true, 300);

    }

    @Override
    public void onRefreshDownComplete() {
        listview.onPullDownRefreshComplete();
    }

    @Override
    public void onRefreshUpComplete() {
        listview.onPullUpRefreshComplete();
    }

    @Override
    public void notifyDataChange() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

}
