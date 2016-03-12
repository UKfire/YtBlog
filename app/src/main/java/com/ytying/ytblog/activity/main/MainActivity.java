package com.ytying.ytblog.activity.main;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.TabHolder;
import com.ytying.ytblog.activity.erke.ErkeFragment;
import com.ytying.ytblog.activity.today.TodayFragment;
import com.ytying.ytblog.activity.tomorrow.TomorrowFragment;
import com.ytying.ytblog.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by UKfire on 15/11/22.
 */
public class MainActivity extends BaseActivity {

    public MainActivity mainActivity;

    private int currentIndex = 0;
    private TabHolder[] mTabList;
    private Fragment[] fgmList;
    private ErkeFragment erkeFragment;
    private TodayFragment todayFragment;
    private TomorrowFragment tomorrowFragment;

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        initUI();
    }

    private void initUI() {
        //创建Fragment
        setContentView(R.layout.act_main);
        todayFragment = new TodayFragment();
        tomorrowFragment = new TomorrowFragment();
        erkeFragment = new ErkeFragment();
        fgmList = new Fragment[]{todayFragment, tomorrowFragment, erkeFragment};
        getFragmentManager().beginTransaction().add(R.id.fragment_container, fgmList[0]).add(R.id.fragment_container, fgmList[1]).add(R.id.fragment_container, fgmList[2]).show(fgmList[0]).hide(fgmList[1]).hide((fgmList[2])).commitAllowingStateLoss();

        //创建Tab
        mTabList = new TabHolder[3];
        mTabList[0] = new TabHolder((RelativeLayout) findViewById(R.id.tabToday));
        mTabList[1] = new TabHolder((RelativeLayout) findViewById(R.id.tabTomorrow));
        mTabList[2] = new TabHolder((RelativeLayout) findViewById(R.id.tabErke));
        for(int i = 0;i < mTabList.length;i ++){
            mTabList[i].getTabLayout().setTag(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeTab(currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == BaseActivity.RESULT_LOGOUT){
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void changeTab(int which) {
        FragmentTransaction trx = getFragmentManager().beginTransaction();
        trx.hide(fgmList[currentIndex]);
        if(!fgmList[which].isAdded())
            trx.add(R.id.fragment_container,fgmList[which]);
        trx.show(fgmList[which]).commitAllowingStateLoss();
        mTabList[currentIndex].getBtIcon().setSelected(false);
        mTabList[currentIndex].getIndicator().setSelected(false);

        mTabList[which].getBtIcon().setSelected(true);
        mTabList[which].getIndicator().setSelected(true);
        currentIndex = which;
    }

    public void onTabClicked(View view){
        int which = (int) view.getTag();
        changeTab(which);
    }

    private static boolean isExit = false;

    @Override
    public void onBackPressed() {
        Timer timer = null;
        if(isExit == false){
            isExit = true;
            Toast.makeText(this,"再按一次退出苹子与桔果",Toast.LENGTH_SHORT).show();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            },2000);
        }else{
            finish();
            System.exit(0);
        }
    }

}
