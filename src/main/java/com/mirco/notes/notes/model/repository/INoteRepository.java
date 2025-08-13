package com.mirco.notes.notes.model.repository;

import com.mirco.notes.notes.model.entitites.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllBySystemUserId(Long userId);
}
