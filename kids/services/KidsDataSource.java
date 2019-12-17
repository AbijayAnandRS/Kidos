package com.example.kids.services;


import com.example.kids.data.ProductData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class KidsDataSource {


    private final Observable.Transformer schedulersTransformer;
    private final KidsService kidsService;

    public KidsDataSource(){
        schedulersTransformer = (Observable.Transformer<Observable, Observable>) observable -> observable
                .doOnError(throwable -> {
                    //TODO need to add Log or crashAnalytics
                            System.out.println("error sheduler here " + throwable.getMessage());
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        kidsService = new KidsService();
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

    public Observable<List<ProductData>> getCategoryData(){
        return kidsService.getProductData().compose(applySchedulers());
    }

    public Observable<Map<String,String>> getPayTmParamMap(String phoneNumber) {
        return kidsService.getPayTmParmMap(phoneNumber).compose(applySchedulers());
    }
}


