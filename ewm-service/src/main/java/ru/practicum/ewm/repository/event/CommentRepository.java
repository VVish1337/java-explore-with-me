package ru.practicum.ewm.repository.event;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.model.event.comment.Comment;

import java.util.List;

/**
 * Class describing jpa comment repository repository
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * Method of repository which find all comment of event
     *
     * @param eventId id of event
     * @return List of Comment
     */
    List<Comment> findAllByEventId(Long eventId);

    /**
     * Method of repository which get all comments by event id list
     *
     * @param ids
     * @param sort
     * @return
     */
    @Query("select o from Comment o where o.event.id in :ids")
    List<Comment> findByEventIds(@Param("ids") List<Long> ids, Sort sort);
}