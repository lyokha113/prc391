package com.cloud.auction.service.impl;

import com.cloud.auction.exception.FirebaseException;
import com.cloud.auction.payload.UploadImg;
import com.cloud.auction.service.FireStoreService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;


@Service
public class FireStoreServiceImpl implements FireStoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FireStoreServiceImpl.class);
    private static final String BIDDING_DOCUMENT = "bidding";

    @Autowired
    private Firestore firestore;

    @Autowired
    private Bucket bucket;

    @Override
    public void insertBidding(String accountId, String accountName, String biddingId, Long price) {
        try {
            DocumentReference docRef = firestore.collection(BIDDING_DOCUMENT).document();
            Map<String, Object> docData = new HashMap<>();
            docData.put("accountId", accountId);
            docData.put("accountName", accountName);
            docData.put("biddingId", biddingId);
            docData.put("price", price);
            docData.put("time", new Date());
            ApiFuture<WriteResult> set = docRef.set(docData);
            set.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage());
            throw new FirebaseException("Insert failed. Firestore error");
        }
    }

    @Override
    public void uploadImages(String path, List<UploadImg> images) {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        List<Blob> blobs = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            try {
                UploadImg image = images.get(i);
                BlobId blobId = BlobId.of(bucket.getName() + "/" + path, String.valueOf(i + 1));
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(image.getContentType()).build();
                Blob blob = storage.create(blobInfo, image.getContent().getBytes());
                blobs.add(blob);
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
                throw new FirebaseException("Upload failed. Storage error");
            }
        }
    }
}
