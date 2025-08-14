package com.mirco.notes.auth.services;

import com.mirco.notes.auth.model.entities.SystemUser;

public interface ISystemUserService {
    /**
     * Retrieves a system user by him/her ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user object if found, or null if not found.
     */
    SystemUser getSystemUserById(Long userId);

}
