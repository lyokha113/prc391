package com.cloud.auction.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    private static final String BUCKET_URL = "prc391-61b27.appspot.com";
    private static String config = "GOOGLE-SERVICE-JSON";

    @Bean
    public Firestore firestore() {
        return FirestoreClient.getFirestore();
    }

    @Bean
    public Bucket bucket() {
        return StorageClient.getInstance().bucket();
    }

    @PostConstruct
    public void init() throws IOException {
        InputStream serviceAccount = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                .setCredentials(credentials)
                .setTimestampsInSnapshotsEnabled(true).build();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setFirestoreOptions(firestoreOptions)
                .setStorageBucket(BUCKET_URL)
                .build();
        FirebaseApp.initializeApp(options);
    }
}
