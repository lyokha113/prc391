package com.cloud.auction.controller;

import com.cloud.auction.exception.AppException;
import com.cloud.auction.model.Bidding;
import com.cloud.auction.model.Product;
import com.cloud.auction.model.ProductImage;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.payload.DeleteImageRequest;
import com.cloud.auction.payload.ProductRequest;
import com.cloud.auction.service.FirebaseService;
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

    @Autowired
    private FirebaseService firebaseService;

    @GetMapping("/product")
    private ResponseEntity<ApiResponse> getProducts() {
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(new ApiResponse<>(true, products));
    }

    @PostMapping("/product")
    private ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = productService.createProduct(request);
        return ResponseEntity.ok(new ApiResponse<>(true, product));
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
    private ResponseEntity<
            ApiResponse> createImage(@RequestParam("file") MultipartFile file, @RequestParam("product") Integer id) {
        String url = firebaseService.createImage("/p" + id, file);
        ProductImage image = productImageService.createImage(id, url);
        return ResponseEntity.ok(new ApiResponse<>(true, image));
    }

    @PostMapping("/product/image/remove")
    private ResponseEntity<ApiResponse> removeImage(@Valid @RequestBody DeleteImageRequest request) {
        firebaseService.deleteImage("/p" + request.getProductId() , request.getFileName());
        productImageService.deleteImage(request.getImageId());
        return ResponseEntity.ok(new ApiResponse<>(true, "deleted successfully"));
    }


}
