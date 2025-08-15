package com.mirco.notes.auth.services.systemUser;

import com.mirco.notes.auth.model.entities.SystemUser;
import com.mirco.notes.auth.model.repository.ISystemUserRepository;
import com.mirco.notes.shared.model.exceptions.UserNotRegisteredException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public SystemUser findSystemUserByEmail(String email) {
        //todo
        return getSystemUserByEmail(email);
    }


    private SystemUser getSystemUserByEmail(String email) {
        final Optional<SystemUser> systemUserOptional = iSystemUserRepository.findSystemUserByEmail(email);
        if(systemUserOptional.isEmpty()) {
            throw new UserNotRegisteredException("User with email " +  email + " is not registered");
        }
        return systemUserOptional.get();
    }

}
