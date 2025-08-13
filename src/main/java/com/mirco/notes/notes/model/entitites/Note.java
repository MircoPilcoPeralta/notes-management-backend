package com.mirco.notes.notes.model.entitites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived = false;

    @ManyToOne
    @JoinColumn(name = "system_user_id", nullable = false)
    private SystemUser systemUser;

    @ManyToOne
    @JoinColumn(name = "label_id")
    private Label label;

    @ManyToMany(mappedBy = "notes")
    private Set<Label> labels;

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
