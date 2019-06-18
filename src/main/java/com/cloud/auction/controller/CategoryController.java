package com.cloud.auction.controller;

import com.cloud.auction.entity.Category;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.repository.CategoryRepository;
import com.cloud.auction.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    private ResponseEntity<ApiResponse> getAllCategory() {
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(new ApiResponse<>(true, categories.isEmpty() ? "empty" : categories));
    }
}
