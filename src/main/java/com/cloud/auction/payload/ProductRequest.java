package com.cloud.auction.payload;

import com.cloud.auction.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    Integer id;
    String name;
    String description;
    Long price;
    Integer categoryId;
    boolean active;

}

