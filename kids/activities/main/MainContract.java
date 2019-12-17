package com.example.kids.activities.main;

import com.example.kids.base.MvpView;
import com.example.kids.data.ProductData;

import java.util.List;

public interface MainContract {

    interface View extends MvpView {
      void onProductsFetched(List<ProductData> productDataList);
    }
    interface Presenter{
       void getProducts();
    }
}
