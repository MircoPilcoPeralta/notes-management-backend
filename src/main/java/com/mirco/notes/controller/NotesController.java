package com.mirco.notes.controller;

import com.mirco.notes.notes.model.Request.CreateNoteRequest;
import com.mirco.notes.notes.model.Response.LabelResponse;
import com.mirco.notes.notes.model.Response.NoteResponse;
import com.mirco.notes.notes.model.entitites.Note;
import com.mirco.notes.service.notes.INoteService;
import com.mirco.shared.model.response.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final INoteService noteService;

    public NotesController(INoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<StandardResponse> getAllNotesFromUserById(final @PathVariable("userId") Long userId) {
        final List<Note> notes = noteService.getAllNotesFromUserById(userId);

        final StandardResponse standardResponse = StandardResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Notes retrieved successfully")
                .data(notes)
                .build();

        return ResponseEntity.ok(standardResponse);
    }

     @PostMapping
     public ResponseEntity<StandardResponse> createNote(@RequestBody final CreateNoteRequest createNoteRequest) {
        final Note createdNote = noteService.createNote(createNoteRequest.title(), createNoteRequest.content(), createNoteRequest.systemUserId());

         final StandardResponse standardResponse = StandardResponse.builder()
                 .statusCode(HttpStatus.CREATED.value())
                 .message("Note created successfully")
                 .data(createdNote)
                 .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
     }

     @GetMapping("/{noteId}")
    public ResponseEntity<StandardResponse> getNoteById(final @PathVariable("noteId") Long noteId) {
        final Note note = noteService.getNoteById(noteId);

         final NoteResponse noteResponse = NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .isArchived(note.getIsArchived())
                .systemUserId(note.getSystemUser().getId())
                .labels(extractLabelsFromNote(note))
                .build();

        final StandardResponse standardResponse = StandardResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Note retrieved successfully")
                .data(noteResponse)
                .build();

        return ResponseEntity.ok(standardResponse);
     }

     private List<LabelResponse> extractLabelsFromNote(final Note note) {
         return note.getLabels().stream()
                 .map(label ->
                         LabelResponse.builder()
                                 .id(label.getId())
                                 .name(label.getName())
                                 .build())
                 .toList();
     }

}
