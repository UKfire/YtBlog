package com.ytying.ytblog.activity.today;


import android.os.Handler;

import com.ytying.ytblog.base.BasePresenter;
import com.ytying.ytblog.constants.SpKey;
import com.ytying.ytblog.model.Design;
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

    List<Design> list = new ArrayList<>();

    public Presenter(IView view) {
        super(view);
    }

    public List<Design> getList() {
        return this.list;
    }

    public void initList() {
        String data = SpUtil.loadString(SpKey.DESIGN_LIST);
        if (!data.equals("")) {
            List<Design> tempList = JsonUtil.string2List(data, Design.class);
            list.addAll(tempList);
        }
    }

    public void runRefresh(int perpage, int page) {
        Network.post(RequestFactory.GetAllDesign(perpage, 1), new Handler(), new CallBack() {
            @Override
            public void onCommon(Response response) {
                getView().onRefreshComplete();
            }

            @Override
            public void onError(Response response) {

            }

            @Override
            public void onSuccess(Response response) {
                SpUtil.saveString(SpKey.DESIGN_LIST, response.getDataString());
                List<Design> tempList = JsonUtil.string2List(response.getDataString(), Design.class);
                if (tempList.size() == 10)
                    getView().addPage();
                list.clear();
                list.addAll(tempList);
                getView().notifyDataChange();
                getView().onRefreshComplete();
            }
        });
    }

    public void runLoad(int perpage, int page) {

        Network.post(RequestFactory.GetAllDesign(perpage, page), new Handler(), new CallBack() {
            @Override
            public void onCommon(Response response) {
                getView().onLoadComplete();
            }

            @Override
            public void onError(Response response) {

            }

            @Override
            public void onSuccess(Response response) {
                List<Design> tempList = JsonUtil.string2List(response.getDataString(), Design.class);
                if (tempList.size() == 10)
                    getView().addPage();
                list.addAll(tempList);
                getView().notifyDataChange();
                getView().onLoadComplete();
            }
        });
    }


}
