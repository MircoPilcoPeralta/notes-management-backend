package com.mirco.notes.note.controller;

import com.mirco.notes.auth.model.entities.SystemUser;
import com.mirco.notes.auth.services.systemUser.ISystemUserService;
import com.mirco.notes.note.model.Request.CreateNoteRequest;
import com.mirco.notes.note.model.Request.UpdateNoteRequest;
import com.mirco.notes.label.model.response.LabelResponse;
import com.mirco.notes.note.model.Response.NoteResponse;
import com.mirco.notes.note.model.dto.NoteFiltersDTO;
import com.mirco.notes.note.model.entitites.Note;
import com.mirco.notes.note.service.INoteService;
import com.mirco.notes.shared.model.response.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@Tag(name = "Notes", description = "Endpoints for managing notes")
public class NotesController {

    private final INoteService iNoteService;
    private final ISystemUserService iSystemUserService;

    public NotesController(INoteService noteService, ISystemUserService iSystemUserService) {
        this.iNoteService = noteService;
        this.iSystemUserService = iSystemUserService;
    }

    @Operation(
        summary = "Get all created notes paginated",
        description = "Retrieves all notes in the system, paginated, with optional filters."
    )
    @GetMapping("/all")
    public ResponseEntity<StandardResponse<Page<NoteResponse>>> getAllNotesPaginated(
            @Validated
            @ModelAttribute NoteFiltersDTO noteFiltersDTO
        ) {
        final Page<Note> notesPaginated = iNoteService.getAllNotesPaginated(noteFiltersDTO);

        final Page<NoteResponse> noteResponses = generateNotesPaginatedResponse(notesPaginated);


        final StandardResponse<Page<NoteResponse>> standardResponse = StandardResponse.<Page<NoteResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Notes retrieved successfully")
                .data(noteResponses)
                .build();

        return ResponseEntity.ok(standardResponse);
    }

    @Operation(
        summary = "Get all notes paginated from the authenticated user",
        description = "Retrieves all notes owned by the authenticated user, paginated, with optional filters."
    )
    @GetMapping()
    public ResponseEntity<StandardResponse<Page<NoteResponse>>> getAllUserNotesPaginated(
            @Validated
            @ModelAttribute NoteFiltersDTO noteFiltersDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        final Page<Note> notesPaginated = iNoteService.getAllNotesPaginated(noteFiltersDTO, userDetails);

        final Page<NoteResponse> noteResponses = generateNotesPaginatedResponse(notesPaginated);

        final StandardResponse<Page<NoteResponse>> standardResponse = StandardResponse.<Page<NoteResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Notes retrieved successfully")
                .data(noteResponses)
                .build();

        return ResponseEntity.ok(standardResponse);
    }

    @Operation(
        summary = "Create a new note",
        description = "Creates a new note for the authenticated user."
    )
    @PostMapping
    public ResponseEntity<StandardResponse<NoteResponse>> createNote(
        @Validated
        @RequestBody final CreateNoteRequest createNoteRequest,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        final Note createdNote = iNoteService.createNote(
                createNoteRequest.title(),
                createNoteRequest.content(),
                userDetails);

        final NoteResponse noteResponse = generateNoteResponse(createdNote);

        final StandardResponse<NoteResponse> standardResponse = StandardResponse.<NoteResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Note created successfully")
                .data(noteResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
    }

    @Operation(
        summary = "Get a note by ID",
        description = "Retrieves a note owned by the authenticated user by its id.",
        parameters = {
            @Parameter(name = "noteId", description = "id of the note to retrieve", required = true)
        }
    )
    @GetMapping("/{noteId}")
    public ResponseEntity<StandardResponse<NoteResponse>> getNoteById(
            @Validated
            @Min(value = 1, message = "note ID must be a positive number")
            @PathVariable("noteId") final Long noteId,
            @AuthenticationPrincipal UserDetails userDetails) {

        final Note note = iNoteService.getNoteIfOwnedByCurrentUser(noteId, userDetails);

        final NoteResponse noteResponse = generateNoteResponse(note);

        final StandardResponse<NoteResponse> standardResponse = StandardResponse.<NoteResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Note retrieved successfully")
                .data(noteResponse)
                .build();

        return ResponseEntity.ok(standardResponse);
    }

    @Operation(
        summary = "Update a note",
        description = "Updates the details of an existing note owned by the authenticated user.",
        parameters = {
            @Parameter(name = "noteId", description = "id of the note to update", required = true)
        }
    )
    @PutMapping("/{noteId}")
    public ResponseEntity<StandardResponse<NoteResponse>> updateNote(
            @Min(value = 1, message = "note ID must be a positive number")
            @PathVariable("noteId") final Long noteId,
            @Validated
            @RequestBody final UpdateNoteRequest updateNoteRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        final Note updatedNote = iNoteService.updateNoteIfOwnedByCurrentUser(noteId, updateNoteRequest, userDetails);

        final NoteResponse noteResponse = generateNoteResponse(updatedNote);

        final StandardResponse<NoteResponse> standardResponse = StandardResponse.<NoteResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Note updated successfully")
                .data(noteResponse)
                .build();

        return ResponseEntity.ok(standardResponse);
    }

    @Operation(
        summary = "Delete a note",
        description = "Deletes a note owned by the authenticated user.",
        parameters = {
            @Parameter(name = "noteId", description = "id of the note to delete", required = true)
        }
    )
    @DeleteMapping("/{noteId}")
    public ResponseEntity<StandardResponse<Boolean>> deleteNote(
            @Validated
            @Min(value = 1, message = "note ID must be a positive number")
            @PathVariable("noteId") final Long noteId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Boolean deleteOperationResult = iNoteService.deleteNoteIfOwnedByCurrentUser(noteId, userDetails);

        final StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Note deleted successfully")
                .data(deleteOperationResult)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @Operation(
        summary = "Toggle archive status",
        description = "Toggles the archive status of a note owned by the authenticated user.",
        parameters = {
            @Parameter(name = "noteId", description = "id of the note to toggle archive status", required = true)
        }
    )
    @PatchMapping("/{noteId}/toggle-archive")
    public ResponseEntity<StandardResponse<NoteResponse>> toggleArchiveStatus(
            @PathVariable("noteId") final Long noteId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        final Note updatedNote = iNoteService.toggleArchiveStatusIfOwnedByCurrentUser(noteId, userDetails);

        final NoteResponse noteResponse = generateNoteResponse(updatedNote);

        final StandardResponse<NoteResponse> standardResponse = StandardResponse.<NoteResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Note archive status toggled successfully")
                .data(noteResponse)
                .build();

        return ResponseEntity.ok(standardResponse);
    }

    private List<LabelResponse> extractLabelsFromNote(final Note note) {
        if (note.getLabels() == null || note.getLabels().isEmpty()) {
            return List.of();
        }
        return note.getLabels().stream()
                .map(label ->
                        LabelResponse.builder()
                                .id(label.getId())
                                .name(label.getName())
                                .build())
                .toList();
    }

    private NoteResponse generateNoteResponse(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .isArchived(note.getIsArchived())
                .systemUserId(note.getSystemUser().getId())
                .labels(extractLabelsFromNote(note))
                .build();
    }

    private Page<NoteResponse> generateNotesPaginatedResponse(Page<Note> notesPaginated) {
        return notesPaginated.map(this::generateNoteResponse);
    }

}
