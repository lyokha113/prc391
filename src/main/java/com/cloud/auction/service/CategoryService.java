package com.cloud.auction.service;

import com.cloud.auction.model.Category;
import com.cloud.auction.payload.CategoryRequest;

import java.util.List;

public interface CategoryService {

    Category getCategory(Integer id);
    List<Category> getCategories();
    Category createCategory(CategoryRequest request);
    void updateCategory(Integer id, CategoryRequest request);
}
