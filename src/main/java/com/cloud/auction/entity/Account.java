package com.cloud.auction.entity;

import com.cloud.auction.constant.RoleEnum;
import com.cloud.auction.payload.CreateAccountRequest;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    public Account(CreateAccountRequest request) {
        Role role = new Role();
        role.setId(RoleEnum.CUSTOMER.getId());
        this.fullName = request.getFullName();
        this.email = request.getEmail();
        this.role = role;
        this.active = true;
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column
    @Type(type = "uuid-char")
    private UUID id;

    @Column(unique = true)
    @NotBlank
    private String email;

    @Column
    @NotBlank
    private String password;

    @Column(columnDefinition = "TINYINT(1) default 0", nullable = false)
    @NotNull
    private Boolean active;

    @Column
    @NotBlank
    private String fullName;

    @Column
    private String address;

    @Column
    private String phone;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Role role;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BiddingHistory> biddingHistories;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
