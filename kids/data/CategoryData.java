package com.example.kids.data;

import java.util.List;

import lombok.Data;

@Data
public class CategoryData {
    public final String category;
    public final List<ProductData> productList;
}
