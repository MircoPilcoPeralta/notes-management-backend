package com.mirco.notes.service.notes;

import com.mirco.notes.notes.model.Request.UpdateNoteRequest;
import com.mirco.notes.notes.model.entitites.Label;
import com.mirco.notes.notes.model.entitites.Note;
import com.mirco.notes.notes.model.entitites.SystemUser;
import com.mirco.notes.notes.model.exceptions.NoteNotFoundException;
import com.mirco.notes.notes.model.repository.INoteRepository;
import com.mirco.notes.service.label.ILabelService;
import com.mirco.notes.service.user.ISystemUserService;
import com.mirco.shared.model.exceptions.UserNotRegisteredException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NoteServiceImpl implements INoteService {

    private final INoteRepository iNoteRepository;
    private final ISystemUserService iSystemUserService;
    private final ILabelService iLabelService;

    public NoteServiceImpl(INoteRepository iNoteRepository, ISystemUserService iSystemUserService, ILabelService iLabelService) {
        this.iNoteRepository = iNoteRepository;
        this.iSystemUserService = iSystemUserService;
        this.iLabelService = iLabelService;
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

    @Override
    public Note updateNote(Long noteId, UpdateNoteRequest updateNoteRequest) {
        Note noteFromDB = getNoteById(noteId);

        noteFromDB.setTitle(updateNoteRequest.title());
        noteFromDB.setContent(updateNoteRequest.content());

        if(updateNoteRequest.isArchived() != null && updateNoteRequest.isArchived() != noteFromDB.getIsArchived()) {
            noteFromDB.setIsArchived(updateNoteRequest.isArchived());
        }

        mergeNoteLabels(updateNoteRequest, noteFromDB);

        return iNoteRepository.save(noteFromDB);
    }

    private void mergeNoteLabels(UpdateNoteRequest updateNoteRequest, Note noteFromDB) {
        final boolean notesFromBDHasLabels = noteFromDB.getLabels() != null && !noteFromDB.getLabels().isEmpty();
        final boolean requestHasLabels = updateNoteRequest.labels() != null && !updateNoteRequest.labels().isEmpty();

        if(!requestHasLabels && notesFromBDHasLabels) {
            noteFromDB.setLabels(Set.of());
        }

        if(requestHasLabels) {
            // compruebo que existan
            updateNoteRequest.labels().forEach(label -> {
                if(label.getId() == null) {
                    throw new IllegalArgumentException("Label ID cannot be null");
                }
                final Label labelFromDB = iLabelService.getLabelById(label.getId());
                if(labelFromDB == null) {
                    throw new IllegalArgumentException("Label with ID " + label.getId() + " does not exist.");
                }
            });

            if(!notesFromBDHasLabels) {
                noteFromDB.setLabels(updateNoteRequest.labels());
            }
            else {
                Set<Label> updatedLabels = new HashSet<>();

                noteFromDB.getLabels().forEach(label -> {
                    if(updateNoteRequest.labels().contains(label)) {
                        updatedLabels.add(label);
                    }
                });

                updateNoteRequest.labels().forEach(labelFromRequest -> {
                    if(!noteFromDB.getLabels().contains(labelFromRequest)) {
                        updatedLabels.add(labelFromRequest);
                    }
                });


                noteFromDB.setLabels(updatedLabels);
            }
        }
    }

    private SystemUser getSystemUserById(Long userId) {
        final SystemUser systemUser = iSystemUserService.getSystemUserById(userId);

        if (systemUser == null) {
            throw new UserNotRegisteredException();
        }
        return systemUser;
    }

}
