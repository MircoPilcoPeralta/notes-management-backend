package com.mirco.shared.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class StandardResponse {
    private Integer statusCode;
    private String message;
    private Object data;
}
