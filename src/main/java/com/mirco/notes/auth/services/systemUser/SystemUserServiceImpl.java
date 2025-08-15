package com.mirco.notes.auth.services.systemUser;

import com.mirco.notes.auth.model.entities.SystemUser;
import com.mirco.notes.auth.model.repository.ISystemUserRepository;
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
