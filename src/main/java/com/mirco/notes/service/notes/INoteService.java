package com.mirco.notes.service.notes;

import com.mirco.notes.notes.model.Request.UpdateNoteRequest;
import com.mirco.notes.notes.model.dto.NoteFiltersDTO;
import com.mirco.notes.notes.model.entitites.Note;
import org.springframework.data.domain.Page;

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


    /**
     * Deletes a note by its ID.
     *
     * @param noteId The ID of the note to delete.
     * @return True if the note was successfully deleted, false otherwise.
     */
    Boolean deleteNote(Long noteId);


    /**
     * Method to obtain all notes paginated.
     *
     * @param noteFiltersDTO filter to apply to the notes.
     * @return a page of notes that match the criteria.
     */
    Page<Note> getAllNotesPaginated(NoteFiltersDTO noteFiltersDTO);

    /**
     * Method to obtain all notes paginated belonging to a specific user.
     *
     * @param noteFiltersDTO filter to apply to the notes.
     * @param userId id of the user.
     * @return a page of notes that match the criteria.
     */
    Page<Note> getAllNotesPaginated(NoteFiltersDTO noteFiltersDTO, Long userId);

}
