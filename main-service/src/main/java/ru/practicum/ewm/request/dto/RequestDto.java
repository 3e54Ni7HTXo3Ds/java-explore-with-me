package ru.practicum.ewm.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestDto {

    private String created;
    private Long event;
    private Long id; // — уникальный идентификатор ;
    private Long requester;
    private String status;
}
