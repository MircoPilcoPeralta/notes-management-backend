package com.mirco.notes.service.notes;

import com.mirco.notes.notes.model.Request.UpdateNoteRequest;
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

    /**
     * Creates a new note for a registered user.
     *
     * @param title   The title of the note.
     * @param content The content of the note.
     * @param userId  The ID of the user creating the note.
     * @return The created Note object.
     */
    Note createNote(String title, String content, Long userId);

    /**
     * Retrieves a specific note by its ID.
     *
     * @param noteId The ID of the note.
     * @return The Note object found.
     */
    Note getNoteById(Long noteId);

    /**
     * Updates an existing note.
     *
     * @param noteId The ID of the note to update.
     * @param updateNoteRequest The note with fields updated.
     * @return The updated Note object.
     */
    Note updateNote(Long noteId, UpdateNoteRequest updateNoteRequest);

}
