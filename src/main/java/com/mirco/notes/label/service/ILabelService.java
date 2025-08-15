package com.mirco.notes.label.service;

import com.mirco.notes.label.model.entities.Label;
import com.mirco.notes.label.model.exceptions.LabelNotOwnedBySystemUserException;
import com.mirco.notes.label.model.request.CreateLabelRequest;
import org.springframework.security.core.userdetails.UserDetails;

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
     * Method to retrieve a system user by their UserDetails.
     *
     * @param userDetails the UserDetails of the user
     * @return the SystemUser associated with the UserDetails
     */
    Label createLabelForCurrentUser(CreateLabelRequest request, UserDetails userDetails);

    /**
     * Method to delete a label if it is owned by the current user.
     *
     * @param labelId the ID of the label to be deleted
     * @param labelIdReassignTo the ID of the label to which notes from the deleted label will be reassigned
     * @param userDetails the UserDetails of the current user
     * @return true if the label was successfully deleted, false otherwise
     * @throws LabelNotOwnedBySystemUserException if the label is not owned by the current user
     */
    Boolean deleteLabelIfOwnedByCurrentUser(Long labelId, Long labelIdReassignTo, UserDetails userDetails);

}
