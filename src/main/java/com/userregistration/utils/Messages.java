package com.userregistration.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {

    MESSAGE_1("User registered with success"),
    MESSAGE_2("Previously registered user"),
    MESSAGE_3("User not found"),
    MESSAGE_4("Invalid credential");

    private final String message;

}
