package ru.practicum.ewm.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationRequestDto {

    private Float lat;
    private Float lon;
}
