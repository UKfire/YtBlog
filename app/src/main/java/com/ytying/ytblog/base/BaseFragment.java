package com.ytying.ytblog.base;

import android.app.Fragment;
import android.widget.Toast;

import com.ytying.ytblog.act.widget.StandardProgressDialog;

/**
 * Created by UKfire on 15/11/22.
 */
public abstract class BaseFragment extends Fragment implements BaseIView {

    StandardProgressDialog progressDialog;

    @Override
    public void showToast(String toast) {
        Toast.makeText(getActivity(),toast,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(String msg) {
        progressDialog = new StandardProgressDialog(getActivity());
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        progressDialog.cancel();
    }

    @Override
    public void finish() {
        ((BaseActivity)getActivity()).finish();
    }

}
