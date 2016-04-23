package com.ytying.ytblog.act.contacts;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.squareup.otto.Subscribe;
import com.ytying.ytblog.R;
import com.ytying.ytblog.YtApp;
import com.ytying.ytblog.act.person.personpage.Act_PersonPage;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.base.BaseActivity;
import com.ytying.ytblog.component.chatbox.ChatBox;
import com.ytying.ytblog.component.pull2refresh.PullToRefreshBase;
import com.ytying.ytblog.component.pull2refresh.PullToRefreshListView;
import com.ytying.ytblog.event.KeyEmotionBoardPopupEvent;
import com.ytying.ytblog.event.ResendMessageEvent;
import com.ytying.ytblog.event.UnreadCountUpdateEvent;
import com.ytying.ytblog.model.domin.User;

import java.util.List;

/**
 * Created by UKfire on 16/4/13.
 */
public class MessageActivity extends BaseActivity implements IView {

    private Presenter presenter;

    private ActionBarLayout actionbar;
    private PullToRefreshListView lvPaopao;
    private ChatBox chatbox;

    private PaopaoAdapter adapter;
    Handler handler = new Handler();

    @Override
    protected void initData() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YtApp.getOtto().register(this);

        setContentView(R.layout.act_singlechat);
        actionbar = (ActionBarLayout) findViewById(R.id.actionbar);
        lvPaopao = (PullToRefreshListView) findViewById(R.id.lvPaopao);
        chatbox = (ChatBox) findViewById(R.id.chatbox);

        presenter = new Presenter(this);
        presenter.initData();
        presenter.onCreate();

        actionbar.setHomeBtnAsBack(this);

        lvPaopao.setPullLoadEnabled(false);
        lvPaopao.setScrollLoadEnabled(false);

        adapter = new PaopaoAdapter(this, presenter.msgList, presenter.objUser);
        lvPaopao.getRefreshableView().setAdapter(adapter);
        lvPaopao.getRefreshableView().setDivider(null);

        //下拉加载更多的实现
        lvPaopao.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                int position = presenter.loadBackMessage();
                if (position > 1)
                    position = position - 1;
                presenter.refreshListView(false, position);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        lvPaopao.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                chatbox.smilePanel.setVisibility(View.GONE);
                return false;
            }
        });

        //从本地数据库拉取以前数据
        presenter.loadBackMessage();

        presenter.refreshListView(false, -2);

        chatbox.setChatBoxCallBack(chatBoxCallBack);

        YtApp.getOtto().post(new UnreadCountUpdateEvent());
    }

    ChatBox.ChatBoxCallBack chatBoxCallBack = new ChatBox.ChatBoxCallBack() {
        @Override
        public void hideKeyBoard() {

        }

        @Override
        public void onSend(String text) {
            presenter.sendText(text);
            scrollTo(-2, 300);
        }

        @Override
        public void onCamera() {

        }

        @Override
        public void onSendPhoto(List<String> list) {

        }

        @Override
        public void onPlus() {
            presenter.refreshListView(true, -2);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void NotifyData() {

        }
    };

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearEditText() {
        chatbox.edittext.setText("");
    }

    @Override
    public void onRefreshComplete() {
        lvPaopao.onPullDownRefreshComplete();
    }

    @Override
    public void scrollTo(final int position, long mills) {
        lvPaopao.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (position == -1)
                    return;
                else if (position == -2 && presenter.msgList.size() > 0) {
                    lvPaopao.getRefreshableView().clearFocus();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            lvPaopao.getRefreshableView().setSelection(presenter.msgList.size() - 1);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            lvPaopao.getRefreshableView().smoothScrollToPosition(position);
                        }
                    });
                }
            }
        }, mills);
    }

    @Override
    public void setActionBar(final User user) {
        if (user != null) {
            actionbar.setTitle(user.getName());
            actionbar.removeOperateButton();
            actionbar.addOperateButton(R.mipmap.nav_bt_homepage, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageActivity.this.startActivity(Act_PersonPage.createIntent(MessageActivity.this, user));
                }
            }, false);
        }
    }

    @Subscribe
    public void onResendMessageEvent(ResendMessageEvent event) {
        presenter.resendMessage(event);
    }

    @Subscribe
    public void onKeyEmotionBoardPopup(KeyEmotionBoardPopupEvent event) {
        if (lvPaopao.getRefreshableView().getLastVisiblePosition() >= adapter.getCount() - 2)
            scrollTo(-2, 300);
    }

    @Override
    protected void onDestroy() {
        YtApp.getOtto().unregister(this);
        super.onDestroy();
    }
}
