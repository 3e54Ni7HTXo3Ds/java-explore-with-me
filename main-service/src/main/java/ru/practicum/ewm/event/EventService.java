package ru.practicum.ewm.event;

import ru.practicum.ewm.error.exceptions.IncorrectParameterException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.model.Event;

public interface EventService {
    Event createEvent(Long userId, EventRequestDto eventRequestDto)
            throws NotFoundParameterException, IncorrectParameterException;
}
