package com.example.kids.activities.productpage;

import com.example.kids.base.MvpView;

import java.util.Map;

public interface ProductContract {

    interface View extends MvpView {

        void paramMapReceived(Map<String, String> checkSum);
    }

    interface Presenter {
      void getPayTmCheckSumHash(String phoneNumber);
    }
}
