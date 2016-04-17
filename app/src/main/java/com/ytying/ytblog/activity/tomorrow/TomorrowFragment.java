package com.ytying.ytblog.activity.tomorrow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Subscribe;
import com.ytying.ytblog.R;
import com.ytying.ytblog.YtApp;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.activity.main.MainActivity;
import com.ytying.ytblog.base.BaseFragment;
import com.ytying.ytblog.event.DeleteConversationEvent;

/**
 * Created by UKfire on 15/11/22.
 */
public class TomorrowFragment extends BaseFragment implements IView {

    Presenter presenter;
    ActionBarLayout actionbar;
    ListView listview;
    ConversationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fgm_communication,container,false);
        actionbar = (ActionBarLayout) v.findViewById(R.id.comm_actionbar);
        listview = (ListView) v.findViewById(R.id.comm_listview);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        YtApp.getOtto().register(this);
        presenter = new Presenter(this);
        actionbar.setTitle("慧装修");
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
        adapter = new ConversationAdapter(getActivity(),presenter.getMessages());
        listview.setAdapter(adapter);

        presenter.refreshListView();
    }

    @Subscribe
    public void onDeleteConversation(DeleteConversationEvent event){
        presenter.refreshListView();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.refreshListView();
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }
}
