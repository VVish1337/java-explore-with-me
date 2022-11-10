package ru.practicum.ewm.dto.event.comment;

import lombok.*;

/**
 * Class which describes comment dto
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long eventId;
    private Long userId;
    private String text;
    private String createdOn;
}
