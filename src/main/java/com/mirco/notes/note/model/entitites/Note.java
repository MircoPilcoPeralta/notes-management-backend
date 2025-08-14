package com.mirco.notes.note.model.entitites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirco.notes.label.model.entities.Label;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "note")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived;

    @ManyToOne
    @JoinColumn(name = "system_user_id", nullable = false)
    @JsonIgnore
    private SystemUser systemUser;

    @ManyToMany
    @JoinTable(
            name = "label_note",
            joinColumns = @JoinColumn(name = "id_note"),
            inverseJoinColumns = @JoinColumn(name = "id_label")
    )
    private Set<Label> labels;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate ;

    @Column(name = "last_modification_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModificationDate;

    @PrePersist
    public void prePersistEntity() {
        isArchived = false;
        creationDate = new Date();
        lastModificationDate = new Date();
    }

    @PreUpdate
    public void preUpdateEntity() {
        lastModificationDate = new Date();
    }

}
