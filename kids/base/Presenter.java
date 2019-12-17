package com.example.kids.base;

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
