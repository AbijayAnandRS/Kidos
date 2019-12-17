package com.example.kids.services;

import com.example.kids.data.ProductData;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;

public class KidsService {

    public static final String BASEURL = "http://192.168.0.102:9000/";

    private final KidsGateway kidsGateway;

    public KidsService() {
        this.kidsGateway = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .client(new OkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(KidsGateway.class);
    }

    Observable<List<ProductData>> getProductData() {
        return kidsGateway.getProductData();
    }

    Observable<Map<String, String>> getPayTmParmMap(String phoneNumber){
        return kidsGateway.getPayTmCheckSum(phoneNumber);
    }
}
