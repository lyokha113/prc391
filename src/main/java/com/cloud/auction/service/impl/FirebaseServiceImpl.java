package com.cloud.auction.service.impl;

import com.cloud.auction.exception.FirebaseException;
import com.cloud.auction.service.FirebaseService;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Service
public class FirebaseServiceImpl implements FirebaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirebaseServiceImpl.class);
    private static final String OFFERS_DOCUMENT = "offers";
    private static final String PRODUCT_IMAGES = "productImages";

    @Autowired
    private Firestore firestore;

    @Autowired
    private Bucket bucket;

    @Override
    public void insertBidding(UUID accountId, String accountName, String biddingId, Long money) {
        try {
            DocumentReference docRef = firestore.collection(OFFERS_DOCUMENT).document();
            Map<String, Object> docData = new HashMap<>();
            docData.put("accountId", accountId.toString());
            docData.put("accountName", accountName);
            docData.put("biddingId", biddingId);
            docData.put("money", money);
            docData.put("time", Timestamp.now());
            ApiFuture<WriteResult> set = docRef.set(docData);
            Timestamp updatedTime = set.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage());
            throw new FirebaseException("Insert failed. Firestore error");
        }
    }

    @Override
    public String createImage(String path, MultipartFile image) {
        Storage storage = bucket.getStorage();
        try {
            BlobId blobId = BlobId.of(bucket.getName(), PRODUCT_IMAGES + path + "/" + image.getName() + "." + image.getContentType());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(image.getContentType())
                    .build();
            Blob blob = storage.create(blobInfo, image.getBytes());
            URL url = blob.signUrl(100, TimeUnit.DAYS);
            return url.toString();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new FirebaseException("Upload failed. Storage error");
        }
    }

    @Override
    public boolean deleteImage(String path, String fileName) {
        Storage storage = bucket.getStorage();
        try {
            BlobId blobId = BlobId.of(bucket.getName(), PRODUCT_IMAGES + path + "/" + fileName);
            return storage.delete(blobId);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new FirebaseException("Upload failed. Storage error");
        }
    }
}
