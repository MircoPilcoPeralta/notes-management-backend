package com.mirco.notes.label.model.repository;

import com.mirco.notes.label.model.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ILabelRepository extends JpaRepository<Label, Long> {

    /**
     * Finds all labels associated with a specific system user.
     *
     * @param userId the id of the system user
     * @return a set of labels associated with the specified user
     */
    Set<Label> findAllBySystemUserId(Long userId);
}
