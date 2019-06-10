package com.cloud.auction.service.impl;

import com.cloud.auction.entity.Account;
import com.cloud.auction.entity.Bidding;
import com.cloud.auction.service.FireStoreService;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FireStoreServiceImpl implements FireStoreService {

    private static String BIDDING_DOCUMENT = "bidding";

    private static Firestore getDBInstance() {
        return FirestoreHelper.INSTANCE;
    }

    private static class FirestoreHelper {
        private static final Firestore INSTANCE = init();

        private static Firestore init() {
            try {
                InputStream serviceAccount = new FileInputStream("gg-auth.json");
                GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(credentials)
                        .build();
                FirebaseApp.initializeApp(options);

                return FirestoreClient.getFirestore();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    @Override
    public boolean insertBidding(Account account, String biddingId, Long price) {

            Firestore db = getDBInstance();
            if (db == null) return false;

            DocumentReference docRef = db.collection(BIDDING_DOCUMENT).document();
            Map<String, Object> docData = new HashMap<>();
            docData.put("accountId", account.getId());
            docData.put("accountName", account.getFullName());
            docData.put("biddingId", biddingId);
            docData.put("price", price);
            docData.put("time", Timestamp.now());
            docRef.set(docData);
            return true;
    }
}
