package com.cloud.auction.service.impl;

import com.cloud.auction.exception.AppException;
import com.cloud.auction.model.Category;
import com.cloud.auction.payload.CategoryRequest;
import com.cloud.auction.repository.CategoryRepository;
import com.cloud.auction.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategory(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryRequest request) {
        List<Category> categories = getCategories();
        Category result = categories.stream()
                .filter(category -> category.getName().equalsIgnoreCase(request.getName()))
                .findAny().orElse(null);
        if (result == null) {
            Category category = new Category();
            category.setName(request.getName());
            category.setActive(true);
            return categoryRepository.save(category);
        } else {
            throw new AppException("name existed");
        }
    }

    @Override
    public void updateCategory(Integer id, CategoryRequest request) {
        Category category = getCategory(id);
        if (category != null) {
            try {
                category.setName(request.getName());
                category.setActive(request.isActive());
                categoryRepository.save(category);
            } catch (Exception ex) {
                throw new AppException("name existed");
            }
        } else {
            throw new AppException("category not found");
        }

    }
}
