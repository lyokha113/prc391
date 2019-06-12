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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class FireStoreServiceImpl implements FireStoreService {
    
    private static String config = "{  \"type\": \"service_account\",  \"project_id\": \"t-door-243518\",  \"private_key_id\": \"5d8401394c994c4c830dba41739fdc324be9faf0\",  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDT7bopXceDJ0Q8\\nkrYXhM86cGV8msZUpGuMBjwNOUnqaI9CWTLwy9KfLQLPR8Msd5PSy6Zuq1fGNP+x\\nqEPwEpb19/DyEfdzilv9SMWsdnC0fLo49OH0tYbYgv2orojg/uMyQSe92gX3F3Lt\\n0RurIafVTcXGLcTG8EsAmG+YYDgr8wGupr3i7tcADtXmENvrMM3Es/Kl1R6Z0zQy\\ndHS0115YzUkC4zAJH4kuIOTlk2h1QUludXwglR/TM2JjJ3lbuE45JTSC2ou9vKDW\\nknaYeImQ9+2bYxMOuLWfp0pWLIYqTQ/UHLYG3PBko8wXZASayhKUlIbldHO5nZQn\\nN6SbhEbnAgMBAAECggEAAULV2wPHV+7aYiqOkE7VvqeWa8YJnuXXqnb/ILiKVdgn\\ne8sKU2Sh9M/nfkG+CcnqLa0OB4zkLZrUs7oM3H1yfsGUolfugIWf/CxaqPYhp4Ju\\nx3BRmhSUQcQWuwkQLKQYZwC/cQGyVQ6e+SVZ8gL2CxBYJg2IGN7cFUyoPw4WwiWD\\nktCl3Q1LSlpB+RuOgYiyTPZX5GL+x+B5J/+TxniEWh+vTLzFznx8dxFnRkwfA98u\\nlOxHYg+d/AscOkfEdIqwEI+7rtlHZT3UHlWrmug21taWb6vqNug1hydUEC8KnWHp\\nr9hoRQqSkOr7fvfKKXzOgy6gGgzKNdQFjNuN5XbW/QKBgQDrWex5UnBjYIyprKCG\\nm82+y/X9YundfnL5lvpBvTlK05TI9wlR09dMVBdxeBpOOHCwtnVZ7M0hIhSBC09Q\\nNWwL49j5p/+sNZ5rvnSmaCIBID1guw2MNocIYcHfMlG6qQ+DS8K5VIP+7WnHWra4\\nRWYqDAq9tIsedr+EQOf0t+O0NQKBgQDmhbjmd5IHom5P9toifDCKz76RxIBMncO8\\n0mtD1N5CTTnxAcDo3pxHlcY0ntZ5CWd4T9drnGwj3Q5ihFB2+HCnS1FqMMv9VwS6\\nnx/Wd9J4vrDuBGFa6qv1tp79NTqQ1W98x78dRYbzBPTxz4rHzHZ1/XfEFw2EXrVF\\nIr7Z5806KwKBgCrq4AuTs/j7T0tVtoQ1f0RuzzHgysLgXzkShhpc1kjF4pjvK5zn\\nY5iAsTG49BsVgb9H1oiKfTGwlEqf9FutEkHsz9FfCGmv8W62cXEzBhhsd6TfF25d\\nNyyZHxCDjZHbaH/J3f2L4vIm+yYmbbGWvjWmsoK7nK72eqA0cdgXfIeNAoGAB4Ux\\nRQN9lhYsdxtcfmeCxeSFGQhKDzXOXuiWaz4UiiEu1Lfz7STmAEfYkXrlEEnIQFqK\\nco5F0N2zTG94mIj+U9bXLxmsbfVayCPMzBhYm2vNJdaqvmaNeZSdlzEXWgY8ftud\\nVn0gK+lrfEADDQWUj8DXGWSPIlRcoYbCpyUywsUCgYBSgqdegFeBQ+N2sXP0xH1I\\nN9kf8PCmjU08xevkPwTI2LXwciGPaX0cpVqbRTTJ8DQE/flm4CQx/PsMO90KlhKE\\na77BcdKV2CsyWke+HAMMhZnP85wlGDnIDnTvNGelmlUmbzUGmoFI2WYMhnMdubx6\\ndWOujqrouyhe5JMgHkTauw==\\n-----END PRIVATE KEY-----\\n\",  \"client_email\": \"firebase-adminsdk-lj94a@t-door-243518.iam.gserviceaccount.com\",  \"client_id\": \"108231255983937225281\",  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",  \"token_uri\": \"https://oauth2.googleapis.com/token\",  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-lj94a%40t-door-243518.iam.gserviceaccount.com\"}";

    private static String BIDDING_DOCUMENT = "bidding";
    private static Firestore db;

    static  {
        try {
//            InputStream serviceAccount = new FileInputStream("./src/gg-auth.json");
            InputStream serviceAccount = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));
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
