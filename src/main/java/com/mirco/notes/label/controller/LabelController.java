package com.mirco.notes.label.controller;


import com.mirco.notes.auth.model.entities.SystemUser;
import com.mirco.notes.auth.services.systemUser.ISystemUserService;
import com.mirco.notes.label.model.entities.Label;
import com.mirco.notes.label.model.request.CreateLabelRequest;
import com.mirco.notes.label.model.response.LabelResponse;
import com.mirco.notes.label.service.ILabelService;
import com.mirco.notes.common.model.response.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
@Tag(name = "Labels", description = "Endpoints to manage  labels")
public class LabelController {
    private final ILabelService iLabelService;
    private final ISystemUserService iSystemUserService;

    public LabelController(ILabelService iLabelService, ISystemUserService iSystemUserService) {
        this.iLabelService = iLabelService;
        this.iSystemUserService = iSystemUserService;
    }

    @Operation(
        summary = "Get all labels from logged-in user",
        description = "Retrieves all labels associated with the authenticated user."
    )
    @GetMapping()
    public ResponseEntity<StandardResponse<List<LabelResponse>>> getAllLabelsFromLoggedUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        SystemUser systemUserFromDB = iSystemUserService.findSystemUserByEmail(userDetails.getUsername());
        Set<Label> labelsFromUser = iLabelService.getAllLabelsByUserId(systemUserFromDB.getId());

        List<LabelResponse> labelResponseList = generateLabelListResponse(labelsFromUser);

        StandardResponse<List<LabelResponse>> response = StandardResponse.<List<LabelResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Labels retrieved successfully")
                .data(labelResponseList)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Create a new label",
        description = "Creates a new label for the authenticated user."
    )
    @PostMapping()
    public ResponseEntity<StandardResponse<LabelResponse>> createLabel(
            @Validated
            @RequestBody
            CreateLabelRequest createLabelRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Label createdLabel = iLabelService.createLabelForCurrentUser(createLabelRequest, userDetails);
        LabelResponse labelResponse = generateLabelResponse(createdLabel);

        StandardResponse<LabelResponse> response = StandardResponse.<LabelResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Label created successfully")
                .data(labelResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("/{labelId}")
    @Operation(
        summary = "Delete a label by id",
        description = "Deletes a label owned by the authenticated user. Optionally, you can reassign associated notes to another label.",
        parameters = {
            @Parameter(name = "labelId", description = "The id of the label to delete", required = true),
            @Parameter(name = "reassignToLabelId", description = "Optional id of another label to reassign notes to")
        }
    )
    public ResponseEntity<StandardResponse<Boolean>> deleteLabelById(
            @PathVariable("labelId") Long labelId,
            @RequestParam(value = "reassignToLabelId", required = false) Long labelIdReassignTo,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Boolean deletionResult = iLabelService.deleteLabelIfOwnedByCurrentUser(labelId, labelIdReassignTo, userDetails);

        StandardResponse<Boolean> response = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
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
