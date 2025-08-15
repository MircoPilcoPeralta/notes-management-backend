package com.mirco.notes.label.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateLabelRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 100 characters")
    String name
) {
}
