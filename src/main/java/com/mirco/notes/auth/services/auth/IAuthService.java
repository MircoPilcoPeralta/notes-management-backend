package com.mirco.notes.auth.services.auth;

import com.mirco.notes.auth.model.request.LoginRequest;
import com.mirco.notes.auth.model.request.RegisterRequest;
import com.mirco.notes.auth.model.response.TokenResponse;

public interface IAuthService {

    /**
     * Method to register a new user
     * @param registerRequest new user's data
     * @return TokenResponse, which contains the token from the user
     * */
    TokenResponse register(RegisterRequest registerRequest);

    /**
     * Method to authenticate a user
     * @param loginRequest user's credentials
     * @return TokenResponse, which contains the token from the user
     * */
    TokenResponse auth(LoginRequest loginRequest);
}
