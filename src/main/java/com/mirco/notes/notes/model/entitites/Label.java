package com.mirco.notes.notes.model.entitites;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "label")
@Getter
@Setter
@NoArgsConstructor
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "system_user_id", nullable = false)
    private SystemUser systemUser;

    @ManyToMany
    @JoinTable(
        name = "label_note",
        joinColumns = @JoinColumn(name = "id_label"),
        inverseJoinColumns = @JoinColumn(name = "id_note")
    )
    private Set<Note> notes;

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
