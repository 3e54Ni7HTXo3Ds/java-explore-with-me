package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.category.dto.CatDto;
import ru.practicum.ewm.location.dto.LocationRequestDto;
import ru.practicum.ewm.user.dto.UserResponseDto;

@Data
@AllArgsConstructor
public class EventResponseDto {

    private String annotation;
    private CatDto category;
    private Long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private Long id;
    private UserResponseDto initiator;
    private LocationRequestDto location;
    private Boolean paid;
    private Long participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private String state;
    private String title;
    private Long views;

}
