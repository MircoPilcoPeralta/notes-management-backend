package com.mirco.notes.label.service;

import com.mirco.notes.label.model.entities.Label;

import java.util.Set;

public interface ILabelService {

    /**
     * Method to retrieve a label by its ID.
     *
     * @param id the ID of the label
     * @return the label with the specified ID
     */
    Label getLabelById(Long id);

    /**
     * Method to retrieve all labels created by a user.
     *
     * @param userId  id of the user.
     * @return a set of labels created by the user.
     */
    Set<Label> getAllLabelsByUserId(Long userId);
}
