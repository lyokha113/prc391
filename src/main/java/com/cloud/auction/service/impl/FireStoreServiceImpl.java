package com.cloud.auction.service.impl;

import com.cloud.auction.service.FireStoreService;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FireStoreServiceImpl implements FireStoreService {

    private static String BIDDING_DOCUMENT = "bidding";
    private static Firestore db;

    static  {
        try {
            InputStream serviceAccount = new FileInputStream("gg-auth.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertBidding(String accountId, String accountName, String biddingId, Long price) {
        if (db != null) {
            DocumentReference docRef = db.collection(BIDDING_DOCUMENT).document();
            Map<String, Object> docData = new HashMap<>();
            docData.put("accountId", accountId);
            docData.put("accountName", accountName);
            docData.put("biddingId", biddingId);
            docData.put("price", price);
            docData.put("time", new Date());
            ApiFuture<WriteResult> future = docRef.set(docData);
            try {
                System.out.println(future.get().getUpdateTime());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
