package com.cloud.auction.service;

import com.cloud.auction.payload.UploadImg;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FirebaseService {
    void insertBidding(UUID accountId, String accountName, String biddingId, Long money);
    public void createPriceList(String biddingId, Long money);
    String createImage(String path, MultipartFile image);
    boolean deleteImage(String path, String image);
}
