package com.mirco.notes.service.notes;

import com.mirco.notes.notes.model.entitites.Note;

import java.util.List;

public interface INoteService {
    /**
     * Retrieves all notes belonging to a registered user.
     *
     * @param userId The ID of the user.
     * @return A list of Note objects for the user.
     */
    List<Note> getAllNotesFromUserById(Long userId);

}
