package com.ytying.ytblog.base;

/**
 * Created by UKfire on 15/11/22.
 */
public class BasePresenter<T extends BaseIView> {

    private T view;

    public BasePresenter(T view){
        this.view = view;
    }

    public T getView(){
        return view;
    }

}
