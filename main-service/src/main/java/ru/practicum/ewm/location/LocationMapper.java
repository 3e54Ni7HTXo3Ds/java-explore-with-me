package ru.practicum.ewm.location;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.location.dto.LocationRequestDto;
import ru.practicum.ewm.location.model.Location;

@Component
public class LocationMapper {

    public static Location toLocation(LocationRequestDto locationRequestDto) {
        return new Location(locationRequestDto.getLat(), locationRequestDto.getLon());
    }

    public static LocationRequestDto toLocationRequestDto(Location location) {
        return new LocationRequestDto(location.getLat(), location.getLon());
    }

}
