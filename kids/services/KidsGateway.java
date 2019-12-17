package com.example.kids.services;


import com.example.kids.data.ProductData;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface KidsGateway {

        @GET("kids/products")
        Observable<List<ProductData>> getProductData();

        @GET("kids/products/payTmCheckSum/{phoneNumber}")
        Observable<Map<String, String>> getPayTmCheckSum(
                @Path("phoneNumber") String phoneNumber
        );
}
