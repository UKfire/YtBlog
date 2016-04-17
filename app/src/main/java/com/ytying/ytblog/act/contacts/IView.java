package com.ytying.ytblog.act.contacts;

import com.ytying.ytblog.base.BaseIView;
import com.ytying.ytblog.model.domin.User;

/**
 * Created by UKfire on 16/4/15.
 */
public interface IView extends BaseIView {

    void notifyDataChanged();

    void clearEditText();

    void onRefreshComplete();

    void scrollTo(int position,long mills);

    void setActionBar(User user);
}
