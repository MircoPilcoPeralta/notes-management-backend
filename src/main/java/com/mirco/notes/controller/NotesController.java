package com.mirco.notes.controller;

import com.mirco.notes.notes.model.Request.CreateNoteRequest;
import com.mirco.notes.notes.model.entitites.Note;
import com.mirco.notes.service.notes.INoteService;
import com.mirco.shared.model.response.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


}
