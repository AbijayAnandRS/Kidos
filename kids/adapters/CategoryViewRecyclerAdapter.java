package com.example.kids.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kids.R;
import com.example.kids.base.BaseActivity;
import com.example.kids.data.CategoryData;
import com.example.kids.data.ProductData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryViewRecyclerAdapter extends RecyclerView.Adapter<CategoryViewRecyclerAdapter.ViewHolder> {

    private final List<CategoryData> categoryDataList;
    private final BaseActivity activity;

    public CategoryViewRecyclerAdapter(BaseActivity activity) {
        categoryDataList = new ArrayList<>();
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.category_view, parent, false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryData categoryData = categoryDataList.get(position);
        if (categoryData != null) {
            holder.tvCategory.setText(categoryData.category);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
            ImageAdapter imageAdapter = new ImageAdapter(activity);
            holder.recyclerView.setAdapter(imageAdapter);
            imageAdapter.updateProductList(categoryData.productList);
        }
    }

    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }

    public void updateProductDataList(List<ProductData> productData){
        categoryDataList.clear();
        Map<String, List<ProductData>> data = getCategoryDataList(productData);
        for (Map.Entry entry : data.entrySet()) {
            List<ProductData> product = (List<ProductData>) entry.getValue();
            categoryDataList.add(new CategoryData(entry.getKey().toString(), product));
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategory;
        RecyclerView recyclerView;

        ViewHolder(View view) {
            super(view);
            recyclerView = view.findViewById(R.id.rv_products);
            tvCategory = view.findViewById(R.id.textCategory);
        }
    }

    private Map<String, List<ProductData>> getCategoryDataList(List<ProductData> productDataList) {
        Map<String, List<ProductData>> categoryDataMap = new HashMap<>();
        for (ProductData productData : productDataList) {
            String[] categories = productData.categories.split(",");
            for (String category : categories) {
                category = category.trim();
                if (categoryDataMap.containsKey(category)) {
                    List<ProductData> data = categoryDataMap.get(category);
                    List<ProductData> newData = new ArrayList<>(data);
                    newData.add(productData);
                    categoryDataMap.put(category, newData);
                } else {
                    categoryDataMap.put(category, Arrays.asList(productData));
                }
            }
        }
        return categoryDataMap;
    }
}
