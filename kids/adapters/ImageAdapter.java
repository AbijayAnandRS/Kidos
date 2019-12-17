package com.example.kids.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kids.R;
import com.example.kids.activities.productpage.ProductpageActivity;
import com.example.kids.base.BaseActivity;
import com.example.kids.data.ProductData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<ProductData> productDataList;
    private BaseActivity activity;

    public ImageAdapter(BaseActivity activity){
        this.productDataList = new ArrayList();
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(activity).inflate(
                R.layout.single_image_view, viewGroup , false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductData productData = productDataList.get(position);
        if(productData!=null){
            holder.tvHeader.setText(productData.getName());
            Picasso.with(activity).load(productData.getImgUrl().split(",")[0]).into(holder.ivImage);
            holder.itemView.setOnClickListener(
                    v -> {
                        activity.getKiddiesDataHolder().put(productData);
                        activity.startActivity(new Intent(activity, ProductpageActivity.class)
                        );
                    }
            );
        }
    }

    public void updateProductList(List<ProductData> productDataList){
        this.productDataList = productDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.productDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvHeader;
        TextView tvDescription;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.product_image);
            tvHeader = itemView.findViewById(R.id.product_name);
        }
    }
}
