package com.mirco.notes.auth.controller;

import com.mirco.notes.auth.model.request.LoginRequest;
import com.mirco.notes.auth.model.request.RegisterRequest;
import com.mirco.notes.auth.model.response.TokenResponse;
import com.mirco.notes.auth.services.auth.IAuthService;
import com.mirco.notes.shared.model.response.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final IAuthService iAuthService;

    public AuthController(IAuthService iAuthService) {
        this.iAuthService = iAuthService;
    }

    @Operation(
        summary = "Register a new user",
        description = "Creates a new user and returns a json web token (JWT) for further requests"
    )
    @PostMapping("/register")
    public ResponseEntity<StandardResponse<TokenResponse>> register(@Validated  @RequestBody RegisterRequest registerRequest) {
        TokenResponse tokenResponse = iAuthService.register(registerRequest);

        StandardResponse<TokenResponse> registerResponse = StandardResponse
                .<TokenResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("User registered successfully")
                .data(tokenResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @Operation(
        summary = "User login",
        description = "Authenticates a user and returns a json web token (JWT) for further requests"
    )
    @PostMapping("/login")
    public ResponseEntity<StandardResponse> login(@Validated @RequestBody LoginRequest loginRequest) {
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
