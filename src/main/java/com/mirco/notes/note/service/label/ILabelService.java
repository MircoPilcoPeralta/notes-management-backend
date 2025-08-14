package com.mirco.notes.note.service.label;

import com.mirco.notes.note.model.entitites.Label;

public interface ILabelService {

    /**
     * Method to retrieve a label by its ID.
     *
     * @param id the ID of the label
     * @return the label with the specified ID
     */
    Label getLabelById(Long id);

}
