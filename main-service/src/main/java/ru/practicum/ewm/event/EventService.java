package ru.practicum.ewm.event;

import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.IncorrectParameterException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventRequestDtoUpdate;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.dto.RequestDtoShort;
import ru.practicum.ewm.request.dto.RequestResponseDtoShort;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.stats.dto.HitResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    Event createEvent(Long userId, EventRequestDto eventRequestDto)
            throws NotFoundParameterException, IncorrectParameterException, ConflictException;

    List<Event> getEvents(Long userId, int from, int size);

    Event getEvent(Long userId, Long eventId, List<HitResponseDto> hitResponseDtos, String uri)
            throws NotFoundParameterException;

    Event updateEvent(Long userId, Long eventId, EventRequestDtoUpdate eventRequestDtoUpdate, Boolean admin)
            throws IncorrectParameterException, ConflictException;

    List<Event> getEventsAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                               String rangeEnd, int from, int size);

    List<Event> getEventsPublic(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd,
                                Boolean onlyAvailable, String sort, int from, int size,
                                List<HitResponseDto> hitResponseDtos,
                                HttpServletRequest httpServletRequest);

    List<Request> getEventRequests(Long userId, Long eventId) throws ConflictException;

    RequestResponseDtoShort updateEventRequests(Long userId, Long eventId, RequestDtoShort requestDtoShort)
            throws ConflictException;
}


