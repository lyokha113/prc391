package com.cloud.auction.service;

import com.cloud.auction.entity.Account;
import com.cloud.auction.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {

    List<Category> getAllCategory();
}
