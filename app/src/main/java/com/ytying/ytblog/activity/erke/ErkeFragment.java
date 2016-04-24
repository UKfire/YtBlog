package com.ytying.ytblog.activity.erke;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.ytying.ytblog.MyUser;
import com.ytying.ytblog.R;
import com.ytying.ytblog.YtApp;
import com.ytying.ytblog.act.person.Act_EditInformation;
import com.ytying.ytblog.act.person.personpage.Act_PersonPage;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.activity.main.MainActivity;
import com.ytying.ytblog.base.BaseFragment;
import com.ytying.ytblog.event.UpdateHeadViewEvent;
import com.ytying.ytblog.utils.ImageLoaderUtil;

/**
 * Created by UKfire on 15/11/22.
 */
public class ErkeFragment extends BaseFragment implements IView {

    Presenter presenter;
    ActionBarLayout actionbar;
    LinearLayout edit, exit;
    RelativeLayout head;
    TextView name;
    ImageView headview, backView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new Presenter(this);
        View v = inflater.inflate(R.layout.fgm_mine, container, false);
        actionbar = (ActionBarLayout) v.findViewById(R.id.mine_actionbar);
        backView = (ImageView) v.findViewById(R.id.mine_back);
        head = (RelativeLayout) v.findViewById(R.id.mine_head);
        exit = (LinearLayout) v.findViewById(R.id.mine_exit);
        edit = (LinearLayout) v.findViewById(R.id.mine_edit);
        name = (TextView) v.findViewById(R.id.mine_name);
        headview = (ImageView) v.findViewById(R.id.mine_headview);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        YtApp.getOtto().register(this);
        actionbar.setTitle("个人中心");

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
        
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Act_PersonPage.createIntent(getActivity(), MyUser.getSelf());
                startActivity(intent);
            }
        });
        ImageLoaderUtil.getImageLoader(getActivity()).displayImage(MyUser.getSelf().getHeadImage(), headview, ImageLoaderUtil.getDioRound());
        ImageLoaderUtil.getImageLoader(getActivity()).displayImage(MyUser.getSelf().getBackImage(), backView, ImageLoaderUtil.getDioGallery());


        name.setText(MyUser.getSelf().getName());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Act_EditInformation.class);
                (getActivity()).startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YtApp.logout(getActivity());
            }
        });
    }

    @Subscribe
    public void updateHeadView(UpdateHeadViewEvent event) {
        ImageLoaderUtil.getImageLoader(getActivity()).displayImage(MyUser.getSelf().getHeadImage(), headview, ImageLoaderUtil.getDioRound());
        ImageLoaderUtil.getImageLoader(getActivity()).displayImage(MyUser.getSelf().getBackImage(), backView, ImageLoaderUtil.getDioGallery());
    }

    @Override
    public void onDestroy() {
        YtApp.getOtto().unregister(this);
        super.onDestroy();
    }
}
