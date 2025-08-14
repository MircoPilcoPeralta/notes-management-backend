package com.mirco.notes.notes.model.repository;

import com.mirco.notes.notes.model.entitites.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface INoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllBySystemUserId(Long userId);

    /**
     * Method to get all notes paginated.
     *
     * @param title the title to search for.
     * @param content the content to search for.
     * @param labelIds the set of label ids to filter by.
     * @param labelsCount the number of labels that must match.
     * @param pageable the pagination information.
     * @return a page of notes that match the criteria.
     */
    @Query(" SELECT n " +
            " FROM Note n " +
            " LEFT JOIN n.labels l " +
            " WHERE (:title IS NULL OR upper(n.title) LIKE concat('%', upper(:title), '%')) " +
            " AND (:content IS NULL OR upper(n.content) LIKE concat('%', upper(:content), '%')) " +
            " AND (:labelIds IS NULL OR l.id IN :labelIds) " +
            " GROUP BY n " +
            " HAVING (:labelIds IS NULL OR COUNT(DISTINCT l.id) = :labelsCount) "
    )
    Page<Note> findAllByTitleLikeAndContentLikeAndLabels_IdIn(
            @Param("title") String title,
            @Param("content") String content,
            @Param("labelIds") Set<Long> labelIds,
            @Param("labelsCount") Integer labelsCount,
            Pageable pageable);
}
