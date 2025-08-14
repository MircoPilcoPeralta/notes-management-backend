package com.mirco.notes.note.model.repository;

import com.mirco.notes.note.model.entitites.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISystemUserRepository extends JpaRepository<SystemUser, Long> {
}
