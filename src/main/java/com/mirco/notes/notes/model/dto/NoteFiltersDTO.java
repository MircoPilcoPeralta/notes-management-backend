package com.mirco.notes.notes.model.dto;

import java.util.Set;

public record NoteFiltersDTO(
        Integer page,
        Integer size,
        String title,
        String content,
        Set<Long> labelIds
) {
    public NoteFiltersDTO {
        if (page == null) page = 0;
        if (size == null) size = 10;
        if (title == null) title = "";
        if (content == null) content = "";
    }
}
