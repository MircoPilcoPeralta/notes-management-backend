package com.mirco.notes.auth.controller;

import com.mirco.notes.auth.model.entities.SystemUser;
import com.mirco.notes.auth.model.response.SystemUserInfoResponse;
import com.mirco.notes.auth.services.systemUser.ISystemUserService;
import com.mirco.notes.shared.model.response.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Endpoints related to retrieve information by the authenticated user.")
public class SystemUserController {

    private final ISystemUserService iSystemUserService;

    public SystemUserController(ISystemUserService iSystemUserService) {
        this.iSystemUserService = iSystemUserService;
    }

    @Operation(
        summary = "Get current authenticated user info",
        description = "Returns the profile information of the authenticated user."
    )
    @GetMapping("/me")
    public ResponseEntity<StandardResponse<SystemUserInfoResponse>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        SystemUser systemUserFromDB = iSystemUserService.findSystemUserByEmail(userDetails.getUsername());

        SystemUserInfoResponse systemUserInfoResponse =  SystemUserInfoResponse.builder()
                .id(systemUserFromDB.getId())
                .email(systemUserFromDB.getEmail())
                .fullName(systemUserFromDB.getFullName())
                .build();

        StandardResponse<SystemUserInfoResponse> response = StandardResponse
                .<SystemUserInfoResponse>builder()
                .statusCode(200)
                .message("User information retrieved successfully")
                .data(systemUserInfoResponse)
                .build();

        return ResponseEntity.ok(response);
    }



}
