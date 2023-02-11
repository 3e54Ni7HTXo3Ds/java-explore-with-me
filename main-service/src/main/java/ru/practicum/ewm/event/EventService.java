package ru.practicum.ewm.event;

import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventResponseDto;

public interface EventService {
    EventResponseDto createEvent(Long userId, EventRequestDto eventRequestDto) throws NotFoundParameterException;
}
