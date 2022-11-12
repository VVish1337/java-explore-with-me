package ru.practicum.ewm.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.event.comment.CommentReport;
import ru.practicum.ewm.model.event.comment.ReportName;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class describing jpa comment report repository repository
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Repository
public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
    /**
     * Method of repository which get all filtered reported comments
     *
     * @param start    start time
     * @param end      end time
     * @param category category of reported comment
     * @return List of CommentReport
     */
    @Query("select a from CommentReport a where a.createdOn between ?1 and ?2 and a.reportName like ?3")
    List<CommentReport> findAllFiltered(LocalDateTime start, LocalDateTime end, ReportName category);

    /**
     * Method of repository which get all reported comments filtered by time
     *
     * @param start start time
     * @param end   end time
     * @return List of CommentReport
     */
    @Query("select a from CommentReport a where a.createdOn between ?1 and ?2")
    List<CommentReport> findAllFilteredWithoutCategory(LocalDateTime start, LocalDateTime end);

    /**
     * Method of repository which get all reported comments by owner
     *
     * @param userId id of user (owner of report)
     * @return List of CommentReport
     */
    List<CommentReport> findAllByReporterId(Long userId);
}
