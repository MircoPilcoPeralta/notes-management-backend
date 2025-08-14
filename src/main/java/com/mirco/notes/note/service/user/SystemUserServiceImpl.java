package com.mirco.notes.note.service.user;

import com.mirco.notes.note.model.entitites.SystemUser;
import com.mirco.notes.note.model.repository.ISystemUserRepository;
import org.springframework.stereotype.Service;

@Service
public class SystemUserServiceImpl implements ISystemUserService {

    private final ISystemUserRepository iSystemUserRepository;

    public SystemUserServiceImpl(ISystemUserRepository iSystemUserRepository) {
        this.iSystemUserRepository = iSystemUserRepository;
    }

    @Override
    public SystemUser getSystemUserById(Long userId) {
        return iSystemUserRepository.findById(userId)
                .orElse(null);
    }
}
