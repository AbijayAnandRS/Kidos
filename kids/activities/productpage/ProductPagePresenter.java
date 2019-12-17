package com.example.kids.activities.productpage;

import com.example.kids.base.BasePresenter;
import com.example.kids.services.KidsDataSource;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

public class ProductPagePresenter extends BasePresenter<ProductContract.View>
        implements ProductContract.Presenter {

    private final KidsDataSource kidsDataSource;

    public ProductPagePresenter() {
        kidsDataSource = new KidsDataSource();
    }

    @Override
    public void getPayTmCheckSumHash(String phoneNumber) {
      view.showProgressbar(true);
      subscription.add(
              kidsDataSource.getPayTmParamMap(phoneNumber)
              .subscribe(new Subscriber<Map<String,String>>() {
                  @Override
                  public void onCompleted() {

                  }

                  @Override
                  public void onError(Throwable e) {
                      if (view != null) {
                          view.showProgressbar(false);
                          view.showThrowable(e);
                      }
                  }

                  @Override
                  public void onNext(Map<String, String> paramMap) {
                      if (view != null) {
                          view.showProgressbar(false);
                          view.paramMapReceived(paramMap);
                      }
                  }
              })
      );
    }
}
