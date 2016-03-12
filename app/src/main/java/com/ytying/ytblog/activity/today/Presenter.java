package com.ytying.ytblog.activity.today;


import com.ytying.ytblog.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UKfire on 15/11/22.
 */
public class Presenter extends BasePresenter<IView> {

    List<Integer> list = new ArrayList<>();

    public Presenter(IView view) {
        super(view);
    }

    public List<Integer> getList() {
        return this.list;
    }

    public void runRefresh() {
        list.add(0, 100);
        list.add(0, 101);
        list.add(0, 102);
    }

    public void runLoad() {
        list.add(999);
        list.add(998);
        list.add(997);
    }

    public void initList() {
        for (int i = 0; i < 10; i++) {
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
        }
    }


}
