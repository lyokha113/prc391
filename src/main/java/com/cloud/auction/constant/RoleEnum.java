package com.cloud.auction.constant;

import java.util.Arrays;

public enum RoleEnum {

    ADMINISTRATOR(1, "ROLE_ADMINISTRATOR"), CUSTOMER(2, "ROLE_CUSTOMER");

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    RoleEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
