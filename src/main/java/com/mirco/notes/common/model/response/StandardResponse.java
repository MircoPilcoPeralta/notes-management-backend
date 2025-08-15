package com.mirco.notes.common.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
}
