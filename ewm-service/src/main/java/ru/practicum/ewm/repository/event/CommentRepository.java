package ru.practicum.ewm.repository.event;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.model.event.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEventId(Long eventId);

    @Query("select o from Comment o where o.event.id in :ids")
    List<Comment> findByEventIds(@Param("ids") List<Long> ids, Sort sort);
}