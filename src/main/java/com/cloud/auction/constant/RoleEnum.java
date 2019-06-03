package com.cloud.auction.constant;

import java.util.Arrays;

public enum RoleEnum {

    ADMINISTRATOR(1), CUSTOMER(2);

    private int id;

    public int getId() {
        return id;
    }

    RoleEnum(int id) {
        this.id = id;
    }

    public static RoleEnum getRoleId(int id) {
        return Arrays.stream(RoleEnum.values()).filter(roleId -> roleId.getId() == id)
                .findFirst().orElse(null);
    }
}
