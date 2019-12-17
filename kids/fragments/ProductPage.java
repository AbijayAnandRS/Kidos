package com.example.kids.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kids.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProductPage extends Fragment {

    private final String imgUrl;

    public  ProductPage(String imgUrl){
        this.imgUrl = imgUrl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_page, container,false);
        ImageView productImage = view.findViewById(R.id.product_image);
        Picasso.with(getActivity()).load(imgUrl).into(productImage);
        return view;
    }
}
