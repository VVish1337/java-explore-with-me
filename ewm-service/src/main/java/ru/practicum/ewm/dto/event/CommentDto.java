package ru.practicum.ewm.dto.event;

import lombok.*;

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
