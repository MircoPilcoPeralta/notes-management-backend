package com.mirco.notes.notes.model.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateNoteRequest(
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    String title,
    @NotNull(message = "Content is required")
    String content,
    @NotNull(message = "userId is required")
    Long systemUserId
) {
}
