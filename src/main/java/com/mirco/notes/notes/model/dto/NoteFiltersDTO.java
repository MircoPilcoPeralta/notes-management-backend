package com.mirco.notes.notes.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record NoteFiltersDTO(
        @Min(0)
        Integer page,
        @Positive
        Integer size,
        @Size(max = 30, message = "Title can have up to 30 characters")
        String title,
        @Size(max = 30, message = "Content can have up to 30 characters")
        String content,
        Set<@Positive(message = "Label ids must be positive") Long> labelIds
) {
    public NoteFiltersDTO {
        if (page == null) page = 0;
        if (size == null) size = 10;
        if (title == null) title = "";
        if (content == null) content = "";
    }
}
