package com.mirco.notes.service.label;

import com.mirco.notes.model.entitites.Label;
import com.mirco.notes.model.repository.ILabelRepository;
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
