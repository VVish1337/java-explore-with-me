package ru.practicum.ewm.dto.event.comment;

import lombok.*;

/**
 * Class which describes comment in event dto
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentInEventDto {
    private String userEmail;
    private String text;
    private String createdOn;
}