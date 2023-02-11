package ru.practicum.ewm.event;

import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.IncorrectParameterException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventRequestDtoUpdate;
import ru.practicum.ewm.event.model.Event;

import java.util.List;

public interface EventService {
    Event createEvent(Long userId, EventRequestDto eventRequestDto)
            throws NotFoundParameterException, IncorrectParameterException, ConflictException;

    List<Event> getEvents(Long userId, int from, int size);

    Event getEvent(Long userId, Long eventId) throws IncorrectParameterException;

    Event updateEvent(Long userId, Long eventId, EventRequestDtoUpdate eventRequestDtoUpdate, Boolean admin)
            throws IncorrectParameterException, ConflictException;
}


