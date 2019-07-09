package com.cloud.auction.utils;

import com.cloud.auction.model.Bidding;
import com.cloud.auction.model.Product;

import java.time.LocalDateTime;

public class BiddingUtils {

    private static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static boolean isCurrent(Bidding bidding) {
        return bidding.getEndTime().isAfter(LocalDateTime.now());
    }

    public static boolean isFinished(Bidding bidding) {
        return bidding.getEndTime().isBefore(LocalDateTime.now()) && bidding.getFinished();
    }

    public static boolean isExpired(Bidding bidding) {
        return bidding.getEndTime().isBefore(LocalDateTime.now()) && bidding.getExpired();
    }

    public static String generateId(Product product) {
        int idLength = 5;
        StringBuilder builder = new StringBuilder();
        while (idLength-- != 0) {
            int character = (int) (Math.random() * ALPHA_STRING.length());
            builder.append(ALPHA_STRING.charAt(character));
        }

        return "P" + product.getId() + builder.toString();
    }
}

