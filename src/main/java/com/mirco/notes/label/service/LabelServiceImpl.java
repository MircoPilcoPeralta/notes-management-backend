package com.mirco.notes.label.service;

import com.mirco.notes.label.model.entities.Label;
import com.mirco.notes.label.model.exceptions.LabelNotFoundException;
import com.mirco.notes.label.model.repository.ILabelRepository;
import com.mirco.notes.label.model.request.CreateLabelRequest;
import com.mirco.notes.auth.model.entities.SystemUser;
import com.mirco.notes.auth.services.systemUser.ISystemUserService;
import com.mirco.notes.note.model.entitites.Note;
import com.mirco.notes.note.model.exceptions.NoteNotOwnedBySystemUserException;
import com.mirco.notes.shared.model.exceptions.UserNotRegisteredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LabelServiceImpl implements ILabelService {

    private final ILabelRepository iLabelRepository;
    private final ISystemUserService iSystemUserService;

    public LabelServiceImpl(ILabelRepository iLabelRepository, ISystemUserService iSystemUserService) {
        this.iLabelRepository = iLabelRepository;
        this.iSystemUserService = iSystemUserService;
    }

    @Override
    public Label getLabelById(Long labelId) {
        Optional<Label> labelOptional = iLabelRepository.findById(labelId);

        if(labelOptional.isEmpty()) {
            throw new LabelNotFoundException("Label with id " + labelId + " not found.");
        }

        return labelOptional.get();
    }

    @Override
    public Set<Label> getAllLabelsByUserId(Long userId) {
        final SystemUser systemUser = getSystemUserById(userId);
        return iLabelRepository.findAllBySystemUserId(systemUser.getId());
    }

    @Override
    public Label createLabelForCurrentUser(CreateLabelRequest createLabelRequest, UserDetails userDetails) {
        SystemUser systemUser = iSystemUserService.findSystemUserByEmail(userDetails.getUsername());

        final Label newLabel = Label.builder()
                .name(createLabelRequest.name())
                .systemUser(systemUser)
                .build();

        iLabelRepository.save(newLabel);

        return newLabel;
    }

    @Override
    public Boolean deleteLabelIfOwnedByCurrentUser(Long labelId, Long labelIdReassignTo, UserDetails userDetails) {
        Label labelFromDB = getLabelIfOwnedByCurrentUser(labelId, userDetails);
        Label targetLabel;

        if (labelIdReassignTo != null) {
            if (labelId.equals(labelIdReassignTo)) {
                throw new IllegalArgumentException("Cannot reassign label to itself.");
            }
            if (!iLabelRepository.existsById(labelIdReassignTo)) {
                throw new LabelNotFoundException("Label to reassign to with id " + labelIdReassignTo + " not found.");
            }
            targetLabel = getLabelById(labelIdReassignTo);
        } else {
            targetLabel = null;
        }

        labelFromDB.getNotes().forEach(note -> {updateNoteLabels(note, labelId, targetLabel);} );

        iLabelRepository.delete(labelFromDB);
        return true;
    }

    private Label getLabelIfOwnedByCurrentUser(Long labelId, UserDetails userDetails) {
        SystemUser systemUserFromDB = iSystemUserService.findSystemUserByEmail(userDetails.getUsername());
        Label label = getLabelById(labelId);

        if (!label.getSystemUser().getId().equals(systemUserFromDB.getId())) {
            throw new NoteNotOwnedBySystemUserException("Label doesn't belong to the logged user");
        }

        return label;
    }


    private void updateNoteLabels(Note note, Long labelIdToRemove, Label labelToAdd) {
        Set<Label> updatedLabels = note.getLabels().stream()
                .filter(label -> !label.getId().equals(labelIdToRemove))
                .collect(Collectors.toSet());

        if (labelToAdd != null) {
            updatedLabels.add(labelToAdd);
        }

        note.setLabels(updatedLabels);
    }

    private SystemUser getSystemUserById(Long userId) {
        final SystemUser systemUser = iSystemUserService.getSystemUserById(userId);

        if (systemUser == null) {
            throw new UserNotRegisteredException();
        }
        return systemUser;
    }

}
