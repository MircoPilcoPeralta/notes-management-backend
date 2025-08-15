package com.mirco.notes.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemUserInfoResponse {
    private Long id;
    private String email;
    private String fullName;
}
