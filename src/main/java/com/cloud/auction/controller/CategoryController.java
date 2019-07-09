package com.cloud.auction.controller;

import com.cloud.auction.exception.AppException;
import com.cloud.auction.model.Category;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    private ResponseEntity<ApiResponse> getCategories() {
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(new ApiResponse<>(true, categories.isEmpty() ? "empty" : categories));
    }

    @PostMapping("/category")
    private ResponseEntity<ApiResponse> createCatagory(@Valid @RequestBody String name) {
        try {
            categoryService.createCategory(name);
            return ResponseEntity.ok(new ApiResponse<>(true, "created successfully"));
        } catch (AppException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/category/{id}")
    private ResponseEntity<ApiResponse> updateCategory(@PathVariable("id") Integer id,
                                                       @RequestBody String name) {
        try {
            categoryService.updateCategory(id, name);
            return ResponseEntity.ok(new ApiResponse<>(true, "updated successfully"));
        } catch (AppException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
