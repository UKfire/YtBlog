package com.ytying.ytblog.act.contacts;

import android.os.Handler;
import android.os.Message;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.ytying.ytblog.base.BasePresenter;
import com.ytying.ytblog.constants.GSession;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.network.Response;
import com.ytying.ytblog.service.UserGetMagic;
import com.ytying.ytblog.service.UserGetThread;
import com.ytying.ytblog.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UKfire on 16/4/15.
 */
public class Presenter extends BasePresenter<IView> {

    public static final int PAGE_SIZE = 10;

    public String objId;

    public User objUser;
    private IMMessage anchor;
    public List<IMMessage> msgList = new ArrayList<>();

    private Handler handler = new Handler();
    private Handler refreshListViewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int position = msg.what;
            getView().onRefreshComplete();
            getView().notifyDataChanged();
            getView().scrollTo(position, 300);
        }
    };

    public Presenter(IView view) {
        super(view);
    }

    public void initData() {
        try {
            InterChat interChat = GSession.getSession().getInterChat();
            objId = interChat.getFunId();
            if (objId.equals("") || objId == null)
                getView().finish();
            else {
                objUser = new User();
                objUser.setFunId(objId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            getView().finish();
        }
    }

    public void onCreate() {
        if (objUser.getName().length() == 0) {
            UserGetMagic.callTask(objId, new UserGetThread.UpdateUIListener() {
                @Override
                public void onSuccess(User user) {
                    objUser.setUser(user);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().notifyDataChanged();
                        }
                    });
                }

                @Override
                public void onFail(Response response) {

                }
            });
        }
    }


    public void loadBackMessage() {
        anchor = anchor == null ? MessageBuilder.createEmptyMessage(objId, SessionTypeEnum.P2P, System.currentTimeMillis()) : anchor;
        loadFormLocal();
    }

    private void loadFormLocal() {
        NIMClient.getService(MsgService.class).queryMessageListEx(anchor, QueryDirectionEnum.QUERY_OLD, PAGE_SIZE, true)
                .setCallback(new RequestCallback<List<IMMessage>>() {
                    @Override
                    public void onSuccess(List<IMMessage> imMessages) {
                        if (imMessages.size() > 0)
                            anchor = imMessages.get(0);
                        if (msgList.size() == 0)
                            msgList.addAll(imMessages);
                        else {
                            List<IMMessage> results = new ArrayList<>();
                            results.addAll(msgList);
                            msgList.clear();
                            msgList.addAll(imMessages);
                            msgList.addAll(results);
                        }
                    }

                    @Override
                    public void onFailed(int i) {

                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }
                });
    }


    public void sendText(String content) {
        IMMessage message = MessageBuilder.createTextMessage(objId, SessionTypeEnum.P2P, content);
        NIMClient.getService(MsgService.class).sendMessage(message, true);
        msgList.add(message);
        getView().clearEditText();
        refreshListView(false, -2);

    }

    public void refreshListView(final boolean regetData, final int scrollPosition) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                if (regetData) {
                    msgList.clear();
                    anchor = null;
                    loadBackMessage();
                }
                refreshListViewHandler.sendEmptyMessage(scrollPosition);
            }
        });
    }

}
