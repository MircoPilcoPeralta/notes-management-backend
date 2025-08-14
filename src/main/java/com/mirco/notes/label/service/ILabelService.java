package com.mirco.notes.label.service;

import com.mirco.notes.label.model.entities.Label;
import com.mirco.notes.label.model.request.CreateLabelRequest;

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


    /**
     * Method to create a new label.
     *
     * @param createLabelRequest the request containing label creation details
     * @return the created label
     */
    Label createLabel(CreateLabelRequest createLabelRequest);


    /**
     * Method to delete a label by its id.
     *
     * @param labelId the ID of the label to be deleted
     * @return true if the label was successfully deleted, false otherwise
     */
    Boolean deleteLabelById(Long labelId);

}
