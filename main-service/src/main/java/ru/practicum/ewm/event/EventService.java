package ru.practicum.ewm.event;

import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.IncorrectParameterException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventRequestDtoUpdate;
import ru.practicum.ewm.event.dto.EventResponseDto;
import ru.practicum.ewm.event.dto.EventResponseDtoShort;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.dto.RequestDtoShort;
import ru.practicum.ewm.request.dto.RequestResponseDtoShort;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    EventResponseDto createEvent(Long userId, EventRequestDto eventRequestDto)
            throws NotFoundParameterException, IncorrectParameterException, ConflictException;

    List<EventResponseDtoShort> getEvents(Long userId, int from, int size, HttpServletRequest httpServletRequest);

    EventResponseDto getEvent(Long userId, Long eventId, HttpServletRequest request)
            throws NotFoundParameterException;

    EventResponseDto updateEvent(Long userId, Long eventId, EventRequestDtoUpdate eventRequestDtoUpdate, Boolean admin,
                                 HttpServletRequest httpServletRequest)
            throws IncorrectParameterException, ConflictException;

    List<EventResponseDto> getEventsAdmin(List<Long> users, List<String> states, List<Long> categories,
                                          String rangeStart,
                                          String rangeEnd, int from, int size, HttpServletRequest httpServletRequest);

    List<EventResponseDto> getEventsPublic(String text, List<Long> categories, Boolean paid, String rangeStart,
                                           String rangeEnd, Boolean onlyAvailable, String sort, int from, int size,
                                           HttpServletRequest httpServletRequest);

    List<RequestDto> getEventRequests(Long userId, Long eventId) throws ConflictException;

    RequestResponseDtoShort updateEventRequests(Long userId, Long eventId, RequestDtoShort requestDtoShort)
            throws ConflictException;
}


