package com.cloud.auction.controller;

import com.cloud.auction.exception.AppException;
import com.cloud.auction.model.Bidding;
import com.cloud.auction.model.Product;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.payload.ProductRequest;
import com.cloud.auction.service.ProductImageService;
import com.cloud.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/product")
    private ResponseEntity<ApiResponse> getProducts() {
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(new ApiResponse<>(true, products.isEmpty() ? "empty" : products));
    }

    @PostMapping("/product")
    private ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        productService.createProduct(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "created successfully"));
    }


    @PutMapping("/product/{id}")
    private ResponseEntity<ApiResponse> updateProduct(@PathVariable("id") Integer id,
                                                      @Valid @RequestBody ProductRequest request) {
        try {
            productService.updateProduct(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "updated successfully"));
        } catch (AppException ex) {
            return ResponseEntity.ok(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping("/product/image/create")
    private ResponseEntity<ApiResponse> createImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(new ApiResponse<>(true, "created successfully"));
    }

    @PostMapping("/product/image/remove")
    private ResponseEntity<ApiResponse> removeImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(new ApiResponse<>(true, "created successfully"));
    }


}
