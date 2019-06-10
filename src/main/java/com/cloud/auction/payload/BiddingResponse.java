package com.cloud.auction.payload;

import com.cloud.auction.entity.Bidding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BiddingResponse {
    private Bidding bidding;
    private List<Integer> bidPrices;
}
