package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.location.dto.LocationRequestDto;
import ru.practicum.ewm.user.dto.UserResponseDto;

@Data
@AllArgsConstructor
public class EventRequestDto {

    private String eventAnnotation;
    private Cat eventCat;
    private Long eventConfirmedRequests;
    private String eventCreated;
    private String eventDescription;
    private String eventDate;
    private Long id;
    private UserResponseDto eventInitiator;
    private LocationRequestDto eventLocation;
    private Boolean eventPaid;
    private Long eventLimit;
    private String eventPublishedOn;
    private Boolean eventRequestModeration;
    private String eventState;
    private String eventTitle;
    private Long eventViews;
}
