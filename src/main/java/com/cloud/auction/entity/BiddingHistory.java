package com.cloud.auction.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bidding_history")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BiddingHistory {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotNull
    private Account account;

    @ManyToOne
    @NotNull
    private Bidding bidding;

    @Column
    @NotNull
    private Long money;

    @Column(columnDefinition = "timestamp default current_timestamp")
    @NotNull
    private LocalDateTime date;

    @Column
    @NotNull
    private int status;

    @Override
    public String toString() {
        return "BiddingHistory{" +
                "id=" + id +
                ", account=" + account +
                ", bidding=" + bidding +
                ", money=" + money +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
