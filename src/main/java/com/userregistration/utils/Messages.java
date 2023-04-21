package com.userregistration.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {

    MESSAGE_1("User registered with success"),
    MESSAGE_2("Previously registered user");

    private final String message;

}
