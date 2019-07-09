package com.cloud.auction.service;

import com.cloud.auction.payload.UploadImg;

import java.util.List;
import java.util.UUID;

public interface FirebaseService {
    void insertBidding(UUID accountId, String accountName, String biddingId, Long money);
    void uploadImages(String path, List<UploadImg> images);
}
