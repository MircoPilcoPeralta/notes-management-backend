package com.mirco.notes.label.service;

import com.mirco.notes.label.model.entities.Label;
import com.mirco.notes.label.model.repository.ILabelRepository;
import com.mirco.notes.note.model.entitites.SystemUser;
import com.mirco.notes.note.service.user.ISystemUserService;
import com.mirco.notes.shared.model.exceptions.UserNotRegisteredException;
import org.springframework.stereotype.Service;

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
    public Label getLabelById(Long id) {
        return iLabelRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Set<Label> getAllLabelsByUserId(Long userId) {
        final SystemUser systemUser = getSystemUserById(userId);
        return iLabelRepository.findAllBySystemUserId(systemUser.getId());
    }


    private SystemUser getSystemUserById(Long userId) {
        final SystemUser systemUser = iSystemUserService.getSystemUserById(userId);

        if (systemUser == null) {
            throw new UserNotRegisteredException();
        }
        return systemUser;
    }
}
