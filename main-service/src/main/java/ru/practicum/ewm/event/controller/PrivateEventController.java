package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.error.exceptions.IncorrectParameterException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.error.exceptions.UpdateException;
import ru.practicum.ewm.event.EventMapper;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventRequestDtoUpdate;
import ru.practicum.ewm.event.dto.EventResponseDto;
import ru.practicum.ewm.event.dto.EventResponseDtoShort;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.event.EventMapper.toEventResponseDto;

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
            throws NotFoundParameterException, IncorrectParameterException {
        return toEventResponseDto(eventService.createEvent(userId, eventRequestDto));
    }

    @GetMapping
    public List<EventResponseDtoShort> getEvents(
            @Positive @PathVariable Long userId,
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
            @Positive @RequestParam(required = false, defaultValue = "10") int size) {
        return eventService.getEvents(userId, from, size).stream()
                .map(EventMapper::toEventResponseDtoShort)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{eventId}")
    public EventResponseDto getEvent(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long eventId) throws IncorrectParameterException {
        return toEventResponseDto(eventService.getEvent(userId, eventId));
    }

    @PatchMapping(path = "/{eventId}")
    public EventResponseDto updateEvent(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long eventId,
            @Valid @RequestBody EventRequestDtoUpdate eventRequestDtoUpdate) throws IncorrectParameterException, UpdateException {
        return toEventResponseDto(eventService.updateEvent(userId, eventId, eventRequestDtoUpdate));
    }


//
//    @GetMapping
//
//
//
//    @PatchMapping
//
//
}
