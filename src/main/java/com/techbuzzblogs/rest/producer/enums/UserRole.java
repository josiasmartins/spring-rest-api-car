package com.techbuzzblogs.rest.producer.enums;

public enum UserRole {
    APPROVER("approver"),
    REQUEST("request");

    private String value;

    UserRole(String value) {
        this.value = value;
    }
}
