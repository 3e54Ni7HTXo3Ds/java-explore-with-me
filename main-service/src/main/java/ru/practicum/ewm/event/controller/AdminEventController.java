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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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
            @Valid @RequestBody EventRequestDtoUpdate eventRequestDtoUpdate, HttpServletRequest httpServletRequest
    )
            throws IncorrectParameterException, ConflictException {
        return eventService.updateEvent(null, eventId, eventRequestDtoUpdate, true, httpServletRequest);
    }

    @GetMapping
    public List<EventResponseDto> getEventsAdmin(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size,
            HttpServletRequest httpServletRequest) {
        return eventService.getEventsAdmin(users, states, categories, rangeStart, rangeEnd, from, size,
                httpServletRequest);
    }


}
