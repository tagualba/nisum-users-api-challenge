package com.nisum.users.statics;

public enum UserStatus {
    ENABLED("enabled"),
    DISABLED("disabled");

    private String value;

    UserStatus(String status) {
        this.value = status;
    }

}

