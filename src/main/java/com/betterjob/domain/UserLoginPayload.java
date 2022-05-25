package com.betterjob.domain;

import lombok.Getter;

@Getter
public class UserLoginPayload {
    private String email;
    private String password;
}
