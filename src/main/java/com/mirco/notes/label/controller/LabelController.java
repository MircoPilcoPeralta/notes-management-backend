package com.mirco.notes.label.controller;


import com.mirco.notes.label.model.entities.Label;
import com.mirco.notes.label.model.request.CreateLabelRequest;
import com.mirco.notes.label.model.response.LabelResponse;
import com.mirco.notes.label.service.ILabelService;
import com.mirco.notes.shared.model.response.StandardResponse;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @PostMapping()
    public ResponseEntity<StandardResponse<LabelResponse>> createLabel(
            @Validated
            @RequestBody
            CreateLabelRequest createLabelRequest) {

        Label createdLabel = iLabelService.createLabel(createLabelRequest);

        LabelResponse labelResponse = generateLabelResponse(createdLabel);

        StandardResponse<LabelResponse> response = StandardResponse.<LabelResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Label created successfully")
                .data(labelResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("/{labelId}")
    public ResponseEntity<StandardResponse<Boolean>> deleteLabel(
            @Validated
            @Min(value = 1, message = "Label id must be a positive number")
            @PathVariable("labelId") Long labelId,
            @Min(value = 1, message = "reassignToLabelId must be a positive number")
            @RequestParam(value = "reassignToLabelId", required = false) Long labelIdReassignTo
    ) {
        Boolean deletionResult = iLabelService.deleteLabelById(labelId, labelIdReassignTo);

        StandardResponse<Boolean> response = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .message("Label deleted successfully")
                .data(deletionResult)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    private static LabelResponse generateLabelResponse(Label createdLabel) {
        return LabelResponse.builder()
                .id(createdLabel.getId())
                .name(createdLabel.getName())
                .build();
    }


    private static List<LabelResponse> generateLabelListResponse(Set<Label> labelsFromUser) {
        return labelsFromUser.stream().map(
                LabelController::generateLabelResponse
        ).toList();
    }


}
