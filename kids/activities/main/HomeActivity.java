package com.example.kids.activities.main;

import android.os.Bundle;
import com.example.kids.R;
import com.example.kids.base.BaseActivity;
import com.example.kids.data.ProductData;
import com.example.kids.fragments.HomeFragment;
import com.example.kids.fragments.ItemFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends BaseActivity implements HomeFragment.CallBack, MainContract.View {

    MainPresenter presenter = new MainPresenter();
    HomeFragment homeFragment = HomeFragment.Companion.newInstance();
    private List<ProductData> productDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (item -> {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.action_home:
                            selectedFragment = homeFragment;
                            break;
                        case R.id.action_notification:
                            selectedFragment = ItemFragment.newInstance();
                            break;
                        case R.id.action_cart:
                            selectedFragment = ItemFragment.newInstance();
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, homeFragment);
        transaction.commit();
        presenter.attachView(this);
        presenter.getProducts();
    }


    @Override
    public BaseActivity getContext() {
     return this;
    }

    @Override
    public void onProductsFetched(List<ProductData> productDataList) {
        this.productDataList = productDataList;
        homeFragment.onProductsFetched(productDataList);
    }

    @NotNull
    @Override
    public List<ProductData> getListProduct() {
        return productDataList;
    }
}
