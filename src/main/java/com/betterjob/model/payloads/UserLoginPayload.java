package com.betterjob.model.payloads;

import lombok.Getter;

@Getter
public class UserLoginPayload {
    private String email;
    private String password;
}
