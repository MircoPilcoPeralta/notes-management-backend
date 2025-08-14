package com.mirco.notes.label.service;

import com.mirco.notes.label.model.entities.Label;
import com.mirco.notes.label.model.exceptions.LabelNotFoundException;
import com.mirco.notes.label.model.repository.ILabelRepository;
import com.mirco.notes.label.model.request.CreateLabelRequest;
import com.mirco.notes.note.model.entitites.SystemUser;
import com.mirco.notes.note.service.user.ISystemUserService;
import com.mirco.notes.shared.model.exceptions.UserNotRegisteredException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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
    public Label createLabel(CreateLabelRequest createLabelRequest) {
        final SystemUser systemUser = getSystemUserById(createLabelRequest.systemUserId());
        final Label newLabel = Label.builder()
                .name(createLabelRequest.name())
                .systemUser(systemUser)
                .build();

        iLabelRepository.save(newLabel);

        return newLabel;
    }

    @Override
    public Boolean deleteLabelById(Long labelId) {
        Label labelFromDB = getLabelById(labelId);
        iLabelRepository.delete(labelFromDB);
        return true;
    }

    private SystemUser getSystemUserById(Long userId) {
        final SystemUser systemUser = iSystemUserService.getSystemUserById(userId);

        if (systemUser == null) {
            throw new UserNotRegisteredException();
        }
        return systemUser;
    }
}
