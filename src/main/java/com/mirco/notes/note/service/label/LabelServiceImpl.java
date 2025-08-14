package com.mirco.notes.note.service.label;

import com.mirco.notes.note.model.entitites.Label;
import com.mirco.notes.note.model.repository.ILabelRepository;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceImpl implements ILabelService {

    private final ILabelRepository iLabelRepository;

    public LabelServiceImpl(ILabelRepository iLabelRepository) {
        this.iLabelRepository = iLabelRepository;
    }

    @Override
    public Label getLabelById(Long id) {
        return iLabelRepository.findById(id)
                .orElse(null);
    }
}
