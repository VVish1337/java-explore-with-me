package ru.practicum.ewm.model.event.comment;

import lombok.*;
import ru.practicum.ewm.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Class describing comment report
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report_comments")
public class CommentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private User reporter;
    @Column(name = "report_name")
    @Enumerated(EnumType.STRING)
    private ReportName reportName;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
}