package com.cloud.auction.service;

import com.cloud.auction.model.Category;

import java.util.List;

public interface CategoryService {

    Category getCategory(Integer id);
    List<Category> getCategories();
    void createCategory(String name);
    void updateCategory(Integer id, String name);
}
