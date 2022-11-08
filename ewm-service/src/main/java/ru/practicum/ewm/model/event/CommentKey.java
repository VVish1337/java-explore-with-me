package ru.practicum.ewm.model.event;

import lombok.*;

import javax.persistence.IdClass;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CommentKey.class)
public class CommentKey implements Serializable {
    private Long event;
}
