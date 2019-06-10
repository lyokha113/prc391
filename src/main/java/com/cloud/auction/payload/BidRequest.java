package com.cloud.auction.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidRequest {

    private String accountId;
    private String bidId;
    private Long money;
}
