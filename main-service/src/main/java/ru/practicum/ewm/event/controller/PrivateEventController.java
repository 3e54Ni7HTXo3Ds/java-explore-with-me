package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.IncorrectParameterException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventRequestDtoUpdate;
import ru.practicum.ewm.event.dto.EventResponseDto;
import ru.practicum.ewm.event.dto.EventResponseDtoShort;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.dto.RequestDtoShort;
import ru.practicum.ewm.request.dto.RequestResponseDtoShort;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponseDto createEvent(@Positive @PathVariable Long userId,
                                        @Valid @RequestBody EventRequestDto eventRequestDto)
            throws NotFoundParameterException, IncorrectParameterException, ConflictException {
        return eventService.createEvent(userId, eventRequestDto);
    }

    @GetMapping
    public List<EventResponseDtoShort> getEvents(
            @Positive @PathVariable Long userId,
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
            @Positive @RequestParam(required = false, defaultValue = "10") int size,
            HttpServletRequest httpServletRequest) {
        return eventService.getEvents(userId, from, size, httpServletRequest);
    }

    @GetMapping(path = "/{eventId}")
    public EventResponseDto getEvent(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long eventId,
            HttpServletRequest httpServletRequest) throws NotFoundParameterException {
        return eventService.getEvent(userId, eventId, httpServletRequest);
    }

    @PatchMapping(path = "/{eventId}")
    public EventResponseDto updateEvent(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long eventId,
            @Valid @RequestBody EventRequestDtoUpdate eventRequestDtoUpdate, HttpServletRequest httpServletRequest)
            throws IncorrectParameterException, ConflictException {
        return eventService.updateEvent(userId, eventId, eventRequestDtoUpdate, false, httpServletRequest);
    }

    @GetMapping(path = "/{eventId}/requests")
    public List<RequestDto> getEventRequests(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long eventId) throws ConflictException {
        return eventService.getEventRequests(userId, eventId);
    }

    @PatchMapping(path = "/{eventId}/requests")
    public RequestResponseDtoShort updateEventRequests(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long eventId,
            @RequestBody RequestDtoShort requestDtoShort)
            throws ConflictException {
        return eventService.updateEventRequests(userId, eventId, requestDtoShort);
    }
}
