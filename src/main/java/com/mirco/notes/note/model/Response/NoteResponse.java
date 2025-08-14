package com.mirco.notes.note.model.Response;

import com.mirco.notes.label.model.response.LabelResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {
    private Long id;
    private String title;
    private String content;
    private Boolean isArchived;
    private Long systemUserId;
    private List<LabelResponse> labels;
}
