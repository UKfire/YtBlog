package com.ytying.ytblog.activity.today;


import android.os.Handler;

import com.ytying.ytblog.base.BasePresenter;
import com.ytying.ytblog.constants.SpKey;
import com.ytying.ytblog.model.Blog;
import com.ytying.ytblog.network.CallBack;
import com.ytying.ytblog.network.Network;
import com.ytying.ytblog.network.RequestFactory;
import com.ytying.ytblog.network.Response;
import com.ytying.ytblog.utils.JsonUtil;
import com.ytying.ytblog.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UKfire on 15/11/22.
 */
public class Presenter extends BasePresenter<IView> {

    List<Blog> list = new ArrayList<>();

    public Presenter(IView view) {
        super(view);
    }

    public List<Blog> getList() {
        return this.list;
    }

    public void initList() {
        String data = SpUtil.loadString(SpKey.DESIGN_LIST);
        if (!data.equals("")) {
            List<Blog> tempList = JsonUtil.string2List(data, Blog.class);
            list.clear();
            list.addAll(tempList);
            getView().notifyDataChange();
            if (tempList.size() >= 1)
                getView().setLastId(tempList.get(tempList.size() - 1).get_id());
            getView().notifyDataChange();
        }
    }

    public void runRefresh(final String lastId) {
        Network.post(RequestFactory.GetBlogList(lastId), new Handler(), new CallBack() {
            @Override
            public void onCommon(Response response) {

            }

            @Override
            public void onError(Response response) {

            }

            @Override
            public void onSuccess(Response response) {
                SpUtil.saveString(SpKey.DESIGN_LIST, response.getDataString());
                List<Blog> tempList = JsonUtil.string2List(response.getDataString(), Blog.class);
                if (tempList.size() >= 1)
                    getView().setLastId(tempList.get(tempList.size() - 1).get_id());
                if (lastId.equals("0"))
                    list.clear();
                list.addAll(tempList);
                getView().notifyDataChange();
                getView().onRefreshDownComplete();
                getView().onRefreshUpComplete();
            }
        });
    }


}
