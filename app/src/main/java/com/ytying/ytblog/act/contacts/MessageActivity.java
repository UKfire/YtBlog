package com.ytying.ytblog.act.contacts;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.base.BaseActivity;
import com.ytying.ytblog.component.chatbox.ChatBox;
import com.ytying.ytblog.component.pull2refresh.PullToRefreshBase;
import com.ytying.ytblog.component.pull2refresh.PullToRefreshListView;

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

        adapter = new PaopaoAdapter(this, presenter.msgList,presenter.objUser);
        lvPaopao.getRefreshableView().setAdapter(adapter);

        //下拉加载更多的实现
        lvPaopao.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        //从本地数据库拉取以前数据
        presenter.loadBackMessage();

        chatbox.setChatBoxCallBack(chatBoxCallBack);
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
}
