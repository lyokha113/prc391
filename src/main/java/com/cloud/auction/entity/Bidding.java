package com.cloud.auction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "bidding")
@NoArgsConstructor
@AllArgsConstructor
public class Bidding {

    @Id
    @NotBlank
    private String orderCode;

    @Column
    @NotNull
    private Long maxMoney;

    @Column
    @NotNull
    private LocalDateTime startTime;

    @Column
    @NotNull
    private LocalDateTime endTime;

    @Column
    @NotNull
    private Boolean expired;

    @Column
    @NotNull
    private Boolean finished;

    @ManyToOne
    @NotNull
    private Product product;

    @OneToMany(mappedBy = "bidding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BiddingHistory> biddingHistories;

    @Override
    public String toString() {
        return "Bidding{" +
                "orderCode='" + orderCode + '\'' +
                ", maxMoney=" + maxMoney +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", expired=" + expired +
                ", finished=" + finished +
                '}';
    }
}
