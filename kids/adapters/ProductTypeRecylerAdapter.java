package com.example.kids.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kids.R;
import com.example.kids.activities.CategoryActivity;
import com.example.kids.data.ProductData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductTypeRecylerAdapter extends RecyclerView.Adapter<ProductTypeRecylerAdapter.ViewHolder> {

    private List<ProductData> productDataList;
    private Activity activity;


    public ProductTypeRecylerAdapter(Activity activity){
       this.productDataList = new ArrayList<>();
       this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.product_view, parent, false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductData productData = productDataList.get(position);
        if(productData != null){
            Picasso.with(activity).load(productData.getImgUrl()).into(holder.img);
            holder.tv1.setText(productData.getName());
            holder.tv2.setText(productData.getDesc());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, CategoryActivity.class));
                }
            });
        }
    }

    public void addProductView(ProductData productData){
        this.productDataList.add(productData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
       return productDataList.size();
    }

     static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv1, tv2;
        ImageView img;

         ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.imagevwpic);
            tv1 = (TextView) view.findViewById(R.id.textView4);
            tv2 = (TextView) view.findViewById(R.id.textView5);
        }
   }
}
