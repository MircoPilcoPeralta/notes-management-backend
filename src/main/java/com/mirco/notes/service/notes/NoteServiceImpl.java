package com.mirco.notes.service.notes;

import com.mirco.notes.notes.model.entitites.Note;
import com.mirco.notes.notes.model.repository.INoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements INoteService{

    private final INoteRepository iNoteRepository;

    public NoteServiceImpl(INoteRepository iNoteRepository) {
        this.iNoteRepository = iNoteRepository;
    }

    @Override
    public List<Note> getAllNotesFromUserById(Long userId) {
        return iNoteRepository.findAllBySystemUserId(userId);
    }


}
