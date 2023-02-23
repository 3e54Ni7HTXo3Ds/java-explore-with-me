package ru.practicum.ewm.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponseDto {

    private Long id; // — уникальный идентификатор ;
    private String text;
    private Long commentator;//
    private Long event;//
    private String date;//
    private String lastDate;//
}
