package com.cloud.auction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@Table(name = "offer")
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotNull
    private Account account;

    @JsonIgnore
    @ManyToOne
    @NotNull
    private Bidding bidding;

    @Column
    @NotNull
    private Long price;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime date;

    @Column
    @NotNull
    private int status;

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", account=" + account +
                ", bidding=" + bidding +
                ", price=" + price +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
