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
    private static String config = "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"prc391-61b27\",\n" +
            "  \"private_key_id\": \"c273f5e00f93aa0cbf2a74af8602b8a750f61293\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC0epTStVkYazjq\\n7Q8SJYWhNXaMfOEzVPHdMwjV+Gp9B403bGydA1rAushx5hznkVSDgi2r7EdUGsGt\\nOik558My8o0X/rIcT8rs4ad1jZW1fPxK01K3v152GHkzgICODFz5GWRUzc7S+VcN\\nLXcj7V13Lin/0oSk64sghYip5NenhjC+aN4meKlhLIzJnbPo4Cu+Ph6ILqXeCMQa\\nkzv25ALEfm7D93PlwpsC2hBLvHM41a30Wmol3dpiDT88saQt1EZ2/lArIbVGvVeS\\ns0CzhkXnFNR0SNkPqfGpEylh/oXnyEqI8D862EFE48CYSodjVkucUuDL0pONzCyI\\ny3O/XacjAgMBAAECggEAM6rsNC5qpUz87HSVXfKUAQTXPXz608E7xMG5g9gsVEL3\\nMKi/p2uwbYCLpd/k4n5+nW+FN7Ta7r8CN19xMfyhq1I5BmeKAgTt6UbotASmmWCA\\nscMx6RzGJDT5bb8aDpCHhWC5knWhAlETlNgxRX3MxjPXHj/cbYJpQBQ6fbjf7DtJ\\nPfPrfk8B5gXxhTSa0fYay4kqt3+bioVZtM+SB5VuplxYstnJ14kj/94AkUE/X8Z2\\nPJjbFuRaXkNt1a31+KjfQ8LZIyyX7BRfKKZQkJUO7InqfSPCRChP03VYZN/09N0h\\nAv65Y4O82FF37gras7DORiJdPNMINXIBvsJBg0NZ4QKBgQDhi6E55UgRy3DlwE1W\\nps7zD0ZzfTKSOTK6IAyppJFodEFOJiwlucz5z0OvVeMR3djIPxQLXiHPtd+kwu2q\\nOVJKLe3SrhDd4LYg+kMjfYYM9aAhBGGPKTESn1G9Pc82r0z2uQZcCPBiMu2/ni8U\\n71SxMLk+KEgLXtE9uoTf2f7R+wKBgQDM2SVxMYTbvT/76tuC9sqBORsdBVTVliRw\\nELi+tJI18lKy3sNa0NrPjVdxpOlx7uzE61jgaEjWNt1YM8x+0VkGgp2lHLYXACBU\\naJtuyx080uFoNrOxYc+7pauJ2zVsroieckm4z8InHOZPI9JbP/jz+HxO2EKcXvqd\\nzQwz0Tge+QKBgEL+AFN8AN4YOV5kshQM0+dqIBjplP3Ns32JS3mQAgiujz8xI2fJ\\nOlLw9z+7GQkThfJkmgWOXiid8uGzbHhlMkYKQluTjDr7Jd3Bt/Nws0JiKr/j1z54\\nk7TeUZ8OHuA1TXzzFCp+FTh1vBxwVbe4JsXCnSMTP+LwtOHK5vC+hWvFAoGBAMfL\\nQumUErD/dFiQSGkDq589w2QavxNSzVc/9/CIzdIv9tjezErXSZMFmDQPVGD7V5L4\\nuEanb+L+wAQ3AUzvgpUG8BsKVc85IE8zd+Yn1OxuActTDxADBklO3HAojYLi9/0S\\nGzVV7VeYqEin95NoYbgSs6ty4gUB3g3q3+vqM4KBAoGAA5FA55NYQilNiypS/OEz\\nl3qcpV9BjIFpZ5Tvyz6oTdCg9/HT5LPab5tNgVACibT52pagDFfUeLR+8Hvz0pcX\\nY4+p66IeXM0uV9BmuW/ocLvtdVIbkhsVZyJOx8wMyw5+66IXGxQMGg5+WPodBSRz\\n85Fgg4i5WhqZ0ZmqFSjfeyI=\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"firebase-adminsdk-wq58d@prc391-61b27.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"115648644134191166890\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-wq58d%40prc391-61b27.iam.gserviceaccount.com\"\n" +
            "}\n";

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
