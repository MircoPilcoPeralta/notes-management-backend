package com.mirco.notes.label.controller;


import com.mirco.notes.label.model.entities.Label;
import com.mirco.notes.label.model.response.LabelResponse;
import com.mirco.notes.label.service.ILabelService;
import com.mirco.notes.shared.model.response.StandardResponse;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/labels")
public class LabelController {
    private final ILabelService iLabelService;

    public LabelController(ILabelService iLabelService) {
        this.iLabelService = iLabelService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<StandardResponse<List<LabelResponse>>> getAllLabelsFromUserByUserId(
            @Validated
            @Min(value = 1, message = "User ID must be a positive number")
            @PathVariable("userId") Long userId) {

        Set<Label> labelsFromUser = iLabelService.getAllLabelsByUserId(userId);

        List<LabelResponse> labelResponseList = generateLabelListResponse(labelsFromUser);

        StandardResponse<List<LabelResponse>> response = StandardResponse.<List<LabelResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Labels retrieved successfully")
                .data(labelResponseList)
                .build();


        return ResponseEntity.ok(response);
    }

    private static List<LabelResponse> generateLabelListResponse(Set<Label> labelsFromUser) {
        return labelsFromUser.stream().map(
                labelEntity -> LabelResponse.builder()
                        .id(labelEntity.getId())
                        .name(labelEntity.getName())
                        .build()
        ).toList();
    }


}
