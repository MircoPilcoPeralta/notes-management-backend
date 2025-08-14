package com.mirco.notes.note.model.Request;

import com.mirco.notes.note.model.entitites.Label;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UpdateNoteRequest(
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    String title,
    @NotNull(message = "Content is required")
    String content,
    Boolean isArchived,
    Set<Label> labels
) {
}
