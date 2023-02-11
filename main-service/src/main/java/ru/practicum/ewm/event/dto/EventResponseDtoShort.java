package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.category.dto.CatDto;
import ru.practicum.ewm.user.dto.UserResponseDto;

@Data
@AllArgsConstructor
public class EventResponseDtoShort {
    private String annotation;
    private CatDto category;
    private Long confirmedRequests;
    private String eventDate;
    private Long id;
    private UserResponseDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}

