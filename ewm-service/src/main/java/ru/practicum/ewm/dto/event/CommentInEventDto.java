package ru.practicum.ewm.dto.event;

import lombok.*;

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