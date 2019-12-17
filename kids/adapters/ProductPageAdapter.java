package com.example.kids.adapters;


import com.example.kids.fragments.ProductPage;
import com.example.kids.fragments.ProductPage1;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ProductPageAdapter extends FragmentStatePagerAdapter {
    private final List<String> imgUrls;
    public ProductPageAdapter(FragmentManager fm, List<String> imgUrls) {
        super(fm);
        this.imgUrls = imgUrls;
    }

    @Override
    public Fragment getItem(int position) {
        return new ProductPage(imgUrls.get(position));
    }

    @Override
    public int getCount() {
        return imgUrls.size();
    }
}
