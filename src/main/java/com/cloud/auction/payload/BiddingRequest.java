package com.cloud.auction.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BiddingRequest {

    private Integer productId;
    private Integer duration;
}
