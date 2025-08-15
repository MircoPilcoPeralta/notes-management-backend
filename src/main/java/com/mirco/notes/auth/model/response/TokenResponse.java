package com.mirco.notes.auth.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse{
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }

}