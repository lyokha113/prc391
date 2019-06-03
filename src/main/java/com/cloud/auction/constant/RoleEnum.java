package com.cloud.auction.constant;

import java.util.Arrays;

public enum Role {

    ADMINISTRATOR(1), CUSTOMER(2);

    private int id;

    public int getId() {
        return id;
    }

    Role(int id) {
        this.id = id;
    }

    public static Role getRoleId(int id) {
        return Arrays.stream(Role.values()).filter(roleId -> roleId.getId() == id)
                .findFirst().orElse(null);
    }
}
