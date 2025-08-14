package com.mirco.notes.controller;

import com.mirco.notes.notes.model.Request.CreateNoteRequest;
import com.mirco.notes.notes.model.Request.UpdateNoteRequest;
import com.mirco.notes.notes.model.Response.LabelResponse;
import com.mirco.notes.notes.model.Response.NoteResponse;
import com.mirco.notes.notes.model.dto.NoteFiltersDTO;
import com.mirco.notes.notes.model.entitites.Note;
import com.mirco.notes.service.notes.INoteService;
import com.mirco.shared.model.response.StandardResponse;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final INoteService iNoteService;

    public NotesController(INoteService noteService) {
        this.iNoteService = noteService;
    }

    @GetMapping()
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

    @GetMapping("/users/{userId}")
    public ResponseEntity<StandardResponse<Page<NoteResponse>>> getAllUserNotesPaginated(
            @Validated
            @ModelAttribute NoteFiltersDTO noteFiltersDTO,
            @PathVariable("userId") final Long userId
    ) {
        final Page<Note> notesPaginated = iNoteService.getAllNotesPaginated(noteFiltersDTO, userId);

        final Page<NoteResponse> noteResponses = generateNotesPaginatedResponse(notesPaginated);

        final StandardResponse<Page<NoteResponse>> standardResponse = StandardResponse.<Page<NoteResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Notes retrieved successfully")
                .data(noteResponses)
                .build();

        return ResponseEntity.ok(standardResponse);
    }

    @GetMapping("/users/{userId}/old")
    public ResponseEntity<StandardResponse<List<NoteResponse>>> getAllNotesFromUserById(
            @Validated
            @Min(value = 1, message = "User ID must be a positive number")
            @PathVariable("userId") final Long userId) {
        final List<Note> notes = iNoteService.getAllNotesFromUserById(userId);

        final List<NoteResponse> noteResponses = generateNoteResponseList(notes);

        final StandardResponse<List<NoteResponse>> standardResponse = StandardResponse.<List<NoteResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Notes retrieved successfully")
                .data(noteResponses)
                .build();

        return ResponseEntity.ok(standardResponse);
    }

    @PostMapping
    public ResponseEntity<StandardResponse<NoteResponse>> createNote(
        @Validated
        @RequestBody final CreateNoteRequest createNoteRequest
    ) {
        final Note createdNote = iNoteService.createNote(
                createNoteRequest.title(),
                createNoteRequest.content(),
                createNoteRequest.systemUserId());

        final NoteResponse noteResponse = generateNoteResponse(createdNote);

        final StandardResponse<NoteResponse> standardResponse = StandardResponse.<NoteResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Note created successfully")
                .data(noteResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<StandardResponse<NoteResponse>> getNoteById(
            @Validated
            @Min(value = 1, message = "note ID must be a positive number")
            @PathVariable("noteId") final Long noteId) {
        final Note note = iNoteService.getNoteById(noteId);

        final NoteResponse noteResponse = generateNoteResponse(note);

        final StandardResponse<NoteResponse> standardResponse = StandardResponse.<NoteResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Note retrieved successfully")
                .data(noteResponse)
                .build();

        return ResponseEntity.ok(standardResponse);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<StandardResponse<NoteResponse>> updateNote(
            @Min(value = 1, message = "note ID must be a positive number")
            @PathVariable("noteId") final Long noteId,
            @Validated
            @RequestBody final UpdateNoteRequest updateNoteRequest) {
        final Note updatedNote = iNoteService.updateNote(noteId, updateNoteRequest);

        final NoteResponse noteResponse = generateNoteResponse(updatedNote);

        final StandardResponse<NoteResponse> standardResponse = StandardResponse.<NoteResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Note updated successfully")
                .data(noteResponse)
                .build();

        return ResponseEntity.ok(standardResponse);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<StandardResponse<Boolean>> deleteNote(
            @Min(value = 1, message = "note ID must be a positive number")
            @PathVariable("noteId") final Long noteId) {

        Boolean deleteOperationResult = iNoteService.deleteNote(noteId);

        final StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Note deleted successfully")
                .data(deleteOperationResult)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
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

    private List<NoteResponse> generateNoteResponseList(List<Note> notes) {
        return notes.stream()
                .map(note -> NoteResponse.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .content(note.getContent())
                        .isArchived(note.getIsArchived())
                        .systemUserId(note.getSystemUser().getId())
                        .labels(extractLabelsFromNote(note))
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
