package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.IncorrectParameterException;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.dto.EventRequestDtoUpdate;
import ru.practicum.ewm.event.dto.EventResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static ru.practicum.ewm.event.EventMapper.toEventResponseDto;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated

@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    @PatchMapping(path = "/{eventId}")
    public EventResponseDto updateEventAdmin(
            @Positive @PathVariable Long eventId,
            @Valid @RequestBody EventRequestDtoUpdate eventRequestDtoUpdate
    )
            throws IncorrectParameterException, ConflictException {
        return toEventResponseDto(eventService.updateEvent(null, eventId, eventRequestDtoUpdate, true));
    }


}
