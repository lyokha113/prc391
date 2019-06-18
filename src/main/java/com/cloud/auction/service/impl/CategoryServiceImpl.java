package com.cloud.auction.service.impl;

import com.cloud.auction.entity.Category;
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
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
