package com.ytying.ytblog.activity.today;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.design.Act_Write_Design;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.act.widget.KeListView;
import com.ytying.ytblog.activity.main.MainActivity;
import com.ytying.ytblog.base.BaseFragment;

/**
 * Created by UKfire on 15/11/22.
 */
public class TodayFragment extends BaseFragment implements IView {

    Presenter presenter;
    ActionBarLayout actionbar;
    KeListView listview;
    TodayAdapter adapter;
    int perpage = 10;
    int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new Presenter(this);
        View v = inflater.inflate(R.layout.fgm_today, container, false);
        actionbar = (ActionBarLayout) v.findViewById(R.id.today_actionbar);
        listview = (KeListView) v.findViewById(R.id.today_listview);
        listview.setDivider(null);
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

        listview.setAdapter(adapter);

        listview.setOnRefreshListener(new KeListView.RefreshListener() {
            @Override
            public void onRefresh() {
                presenter.runRefresh(perpage, page);
            }

            @Override
            public void onLoad() {
                presenter.runLoad(perpage, page);
            }
        });

        if (presenter.getList().size() == 0)
            presenter.runRefresh(10, 1);

    }

    @Override
    public void onRefreshComplete() {
        listview.onRefreshComplete();
    }

    @Override
    public void onLoadComplete() {
        listview.onLoadComplete();
    }

    @Override
    public void notifyDataChange() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void addPage() {
        page += 1;
    }

    @Override
    public void clearPage() {
        page = 1;
    }
}
