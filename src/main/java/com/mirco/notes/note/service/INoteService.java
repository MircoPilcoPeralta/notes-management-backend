package com.mirco.notes.note.service;

import com.mirco.notes.note.model.Request.UpdateNoteRequest;
import com.mirco.notes.note.model.dto.NoteFiltersDTO;
import com.mirco.notes.note.model.entitites.Note;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

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

    /**
     * Method to toggle the archive status of a note by its id.
     *
     * @param noteId of the note to toggle.
     * @return updated Note object with the new archive status.
     */
    Note toggleArchiveStatusById(Long noteId);

    /**
     * Method to check if a note is owned by the current user.
     *
     * @param noteId the id of the note to check.
     * @param userDetails the details of the current user.
     * @return Note object if it is owned by the current user, otherwise throws NoteNotOwnedBySystemUserException.
     */
    Note getNoteIfOwnedByCurrentUser(Long noteId, UserDetails userDetails);

    /**
     * Method to update a note if it is owned by the current user.
     *
     * @param noteId the id of the note to update.
     * @param request the request containing the updated fields.
     * @param userDetails the details of the current user.
     * @return Note object with updated fields if it is owned by the current user, otherwise throws NoteNotOwnedBySystemUserException.
     */
    Note updateNoteIfOwnedByCurrentUser(Long noteId, UpdateNoteRequest request, UserDetails userDetails);

    /**
     * Method to delete a note if it is owned by the current user.
     *
     * @param noteId the id of the note to delete.
     * @param userDetails the details of the current user.
     * @return true if the note was successfully deleted, false otherwise.
     */
    Boolean deleteNoteIfOwnedByCurrentUser(Long noteId, UserDetails userDetails);

    /**
     * Method to toggle the archive status of a note if it is owned by the current user.
     *
     * @param noteId the id of the note to toggle.
     * @param userDetails the details of the current user.
     * @return Note object with updated archive status if it is owned by the current user, otherwise throws NoteNotOwnedBySystemUserException.
     */
    Note toggleArchiveStatusIfOwnedByCurrentUser(Long noteId, UserDetails userDetails);
}
