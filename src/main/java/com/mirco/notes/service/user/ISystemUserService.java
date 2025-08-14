package com.mirco.notes.service.user;

import com.mirco.notes.model.entitites.SystemUser;

public interface ISystemUserService {
    /**
     * Retrieves a system user by him/her ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user object if found, or null if not found.
     */
    SystemUser getSystemUserById(Long userId);

}
