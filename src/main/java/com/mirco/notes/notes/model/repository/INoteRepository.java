package com.mirco.notes.notes.model.repository;

import com.mirco.notes.notes.model.entitites.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INoteRepository extends JpaRepository<Note, Long> {
}
