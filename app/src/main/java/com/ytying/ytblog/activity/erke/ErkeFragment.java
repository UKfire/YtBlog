package com.ytying.ytblog.activity.erke;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ytying.ytblog.MyUser;
import com.ytying.ytblog.R;
import com.ytying.ytblog.act.person.Act_PersonPage;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.base.BaseFragment;

/**
 * Created by UKfire on 15/11/22.
 */
public class ErkeFragment extends BaseFragment implements IView {

    Presenter presenter;
    ActionBarLayout actionbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new Presenter(this);
        View v = inflater.inflate(R.layout.fgm_mine, container, false);
        actionbar = (ActionBarLayout) v.findViewById(R.id.mine_actionbar);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionbar.setTitle("个人中心");
        actionbar.addTextButton("个人主页", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = Act_PersonPage.createIntent(getActivity(), MyUser.getSelf());
                startActivity(intent);
            }
        }, false);
    }
}
