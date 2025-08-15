package com.mirco.notes.auth.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirco.notes.label.model.entities.Label;
import com.mirco.notes.note.model.entitites.Note;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
import java.util.List;

@Entity
@Table(name = "system_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @OneToMany(mappedBy = "systemUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Label> labels;

    @OneToMany(mappedBy = "systemUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Note> notes;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate ;

    @Column(name = "last_modification_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModificationDate;

    @PrePersist
    public void prePersistEntity() {
        creationDate = new Date();
        lastModificationDate = new Date();
    }

    @PreUpdate
    public void preUpdateEntity() {
        lastModificationDate = new Date();
    }
}
