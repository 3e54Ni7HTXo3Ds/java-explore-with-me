package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.location.dto.LocationRequestDto;


@Data
@AllArgsConstructor
public class EventRequestDtoUpdate {

    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private LocationRequestDto location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String stateAction;
    private String title;

}



