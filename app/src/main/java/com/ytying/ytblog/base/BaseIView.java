package com.ytying.ytblog.base;

/**
 * Created by ukfire on 15-11-20.
 */
public interface BaseIView {

    void showToast(String toast);

    void showLoading(String msg);

    void stopLoading();

    void finish();
}
