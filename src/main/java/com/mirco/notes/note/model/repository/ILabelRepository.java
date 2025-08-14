package com.mirco.notes.note.model.repository;

import com.mirco.notes.note.model.entitites.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILabelRepository extends JpaRepository<Label, Long> {
}
