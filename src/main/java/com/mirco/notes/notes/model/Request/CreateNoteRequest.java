package com.mirco.notes.notes.model.Request;

public record CreateNoteRequest(
    String title,
    String content,
    Long systemUserId
) {
}
