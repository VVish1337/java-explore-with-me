package ru.practicum.ewm.dto.event.comment;

import lombok.*;
import ru.practicum.ewm.model.event.comment.ReportName;

/**
 * Class which describes comment report dto
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentReportDto {
    private Long id;
    private CommentDto comment;
    private Long reporterId;
    private ReportName reportName;
    private String createdOn;
}