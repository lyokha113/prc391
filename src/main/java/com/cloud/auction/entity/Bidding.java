package com.cloud.auction.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Bidding {

    @Id
    @Column
    private String id;

    @Column
    @NotNull
    private Long currentPrice;

    @Column(columnDefinition = "timestamp default current_timestamp")
    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @Column(columnDefinition = "TINYINT(1) default 0", nullable = false)
    @NotNull
    private Boolean expired;

    @Column(columnDefinition = "TINYINT(1) default 0", nullable = false)
    @NotNull
    private Boolean finished;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "bidding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BiddingHistory> biddingHistories;

    @Override
    public String toString() {
        return "Bidding{" +
                "id='" + id + '\'' +
                ", currentPrice=" + currentPrice +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", expired=" + expired +
                ", finished=" + finished +
                '}';
    }
}
