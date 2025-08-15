package com.mirco.notes.auth.model.repository;

import com.mirco.notes.auth.model.entities.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISystemUserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findSystemUserByEmail(final String email);
}
