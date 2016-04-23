package com.ytying.ytblog.activity.today;

import com.ytying.ytblog.base.BaseIView;

/**
 * Created by UKfire on 15/11/22.
 */
public interface IView extends BaseIView{

    void onRefreshDownComplete();

    void onRefreshUpComplete();

    void notifyDataChange();

    void setLastId(String lastId);
}
