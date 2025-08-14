package com.mirco.notes.service.notes;

import com.mirco.notes.notes.model.entitites.Note;
import com.mirco.notes.notes.model.entitites.SystemUser;
import com.mirco.notes.notes.model.exceptions.NoteNotFoundException;
import com.mirco.notes.notes.model.repository.INoteRepository;
import com.mirco.notes.service.user.ISystemUserService;
import com.mirco.shared.model.exceptions.UserNotRegisteredException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements INoteService{

    private final INoteRepository iNoteRepository;
    private final ISystemUserService iSystemUserService;

    public NoteServiceImpl(INoteRepository iNoteRepository, ISystemUserService iSystemUserService) {
        this.iNoteRepository = iNoteRepository;
        this.iSystemUserService = iSystemUserService;
    }

    @Override
    public List<Note> getAllNotesFromUserById(Long userId) {
        final SystemUser systemUser = getSystemUserById(userId);
        return iNoteRepository.findAllBySystemUserId(systemUser.getId());
    }

    @Override
    public Note createNote(String title, String content, Long userId) {
        final SystemUser systemUser = getSystemUserById(userId);

        final Note note = Note.builder()
                .title(title)
                .content(content)
                .systemUser(systemUser)
                .build();

        iNoteRepository.save(note);

        return note;
    }

    @Override
    public Note getNoteById(Long noteId) {
        Optional<Note> noteOptional = iNoteRepository.findById(noteId);

        if(noteOptional.isEmpty()) {
            throw new NoteNotFoundException("Note with ID " + noteId + " not found.");
        }

        return noteOptional.get();
    }

    private SystemUser getSystemUserById(Long userId) {
        final SystemUser systemUser = iSystemUserService.getSystemUserById(userId);

        if (systemUser == null) {
            throw new UserNotRegisteredException();
        }
        return systemUser;
    }

}
