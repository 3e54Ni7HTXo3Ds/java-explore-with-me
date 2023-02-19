package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.location.dto.LocationRequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class EventRequestDto {
    @NotNull
    private String annotation;
    @NotNull
    private Long category;
    private String description;
    @NotBlank
    @NotNull
    private String eventDate;
    @NotNull
    private LocationRequestDto location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;

}
