package com.example.kids.activities.main;

import com.example.kids.base.BasePresenter;
import com.example.kids.data.ProductData;
import com.example.kids.services.KidsDataSource;

import java.util.List;

import rx.Subscriber;


public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private final KidsDataSource kidsDataSource;

    public MainPresenter() {
        kidsDataSource = new KidsDataSource();
    }


    @Override
    public void getProducts() {
     view.showProgressbar(true);
     kidsDataSource.getCategoryData()
             .subscribe(new Subscriber<List<ProductData>>() {
                 @Override
                 public void onCompleted() {

                 }

                 @Override
                 public void onError(Throwable e) {
                     if(view!=null){
                      view.showProgressbar(false);
                      view.showThrowable(e);
                     }
                 }

                 @Override
                 public void onNext(List<ProductData> productDataList) {
                     if(view!=null){
                         view.showProgressbar(false);
                         view.onProductsFetched(productDataList);
                     }
                 }
             });

    }
}
