package com.example.kids.base;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment implements MvpView {

    @Override
    public void showProgressDialog(String title, String subTitle) {
        if (getContext() != null && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showProgressDialog(title, subTitle);
        }
    }

    @Override
    public void showProgressbar(boolean show) {
        if (getContext() != null && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showProgressbar(show);
        }
    }

    @Override
    public void showThrowable(Throwable throwable) {
        if (getContext() != null && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showThrowable(throwable);
        }
    }

    protected void showToast(String message) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showToast(getActivity(), message);
        }
    }
}
