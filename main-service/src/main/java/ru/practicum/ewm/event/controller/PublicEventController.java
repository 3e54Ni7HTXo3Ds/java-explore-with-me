package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.dto.EventResponseDto;
import ru.practicum.ewm.stats.client.StatsClient;
import ru.practicum.ewm.stats.dto.HitResponseDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;

    private final StatsClient statsClient;

    @GetMapping(path = "/{eventId}")
    public EventResponseDto getEventPublic(
            @Positive @PathVariable Long eventId,
            HttpServletRequest request) throws NotFoundParameterException {

        statsClient.createHit(request);
        List<HitResponseDto> hits = statsClient.getHits(null, null, request.getRequestURI(), false).getBody();

        return eventService.getEvent(null, eventId, hits, request.getRequestURI());
    }

    @GetMapping
    public List<EventResponseDto> getEventsPublic(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false, defaultValue = "false") Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false, defaultValue = "EVENT_DATE") String sort,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size,
            HttpServletRequest request) {

        statsClient.createHit(request);
        List<HitResponseDto> hits = statsClient.getHits(null, null, request.getRequestURI(), false).getBody();

        return eventService.getEventsPublic(
                        text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, hits, request);
    }
}
