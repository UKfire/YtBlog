package com.ytying.ytblog.activity.tomorrow;


import android.os.Handler;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.ytying.ytblog.base.BasePresenter;
import com.ytying.ytblog.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UKfire on 15/11/22.
 */
public class Presenter extends BasePresenter<IView> {

    public Handler handler = new Handler();
    private List<RecentContact> messages = new ArrayList<>();

    public Presenter(IView view) {
        super(view);
    }

    public void refreshListView() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                messages.clear();
                RefreshRecentContactList();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().notifyDataChanged();
                    }
                });
            }
        });
    }

    public void RefreshRecentContactList() {
        if (messages == null || messages.size() == 0) {
            NIMClient.getService(MsgService.class).queryRecentContacts()
                    .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                        @Override
                        public void onResult(int code, List<RecentContact> recents, Throwable e) {
                            // recents参数即为最近联系人列表（最近会话列表）
                            messages.clear();
                            if (recents != null)
                                messages.addAll(recents);
                        }
                    });
        }
    }

    public List<RecentContact> getMessages() {
        return this.messages;
    }

}
