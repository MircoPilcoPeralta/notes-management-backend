package com.mirco.notes.auth.controller;

import com.mirco.notes.auth.model.request.LoginRequest;
import com.mirco.notes.auth.model.request.RegisterRequest;
import com.mirco.notes.auth.model.response.TokenResponse;
import com.mirco.notes.auth.services.auth.IAuthService;
import com.mirco.notes.shared.model.response.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService iAuthService;

    public AuthController(IAuthService iAuthService) {
        this.iAuthService = iAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<StandardResponse<TokenResponse>> register(@RequestBody RegisterRequest registerRequest) {
        TokenResponse tokenResponse = iAuthService.register(registerRequest);

        StandardResponse<TokenResponse> registerResponse = StandardResponse
                .<TokenResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("User registered successfully")
                .data(tokenResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<StandardResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = iAuthService.auth(loginRequest);

        StandardResponse<TokenResponse> loginResponse = StandardResponse
                .<TokenResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successful login")
                .data(tokenResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

}
