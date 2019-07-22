package com.cloud.auction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Data
@Table(name = "bidding")
@NoArgsConstructor
@AllArgsConstructor
public class Bidding {

    @Id
    @Column
    private String id;

    @Column
    @NotNull
    private Long currentPrice;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @Column(columnDefinition = "TINYINT(1) default 0", nullable = false)
    private Boolean expired;

    @Column(columnDefinition = "TINYINT(1) default 0", nullable = false)
    @NotNull
    private Boolean finished;

    @ManyToOne
    @NotNull
    private Product product;

    @ManyToOne
    private Account winner;

    @OneToMany(mappedBy = "bidding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers;

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
