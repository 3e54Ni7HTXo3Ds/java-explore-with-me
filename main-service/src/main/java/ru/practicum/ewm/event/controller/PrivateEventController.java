package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.error.exceptions.IncorrectParameterException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventResponseDto;

import javax.validation.Valid;

import static ru.practicum.ewm.event.EventMapper.toEventResponseDto;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponseDto createEvent(@PathVariable Long userId,
                                        @Valid @RequestBody EventRequestDto eventRequestDto)
            throws NotFoundParameterException, IncorrectParameterException {
        return toEventResponseDto(eventService.createEvent(userId, eventRequestDto));
    }

//    @GetMapping
//
//
//    @GetMapping
//
//    @GetMapping
//
//
//
//    @PatchMapping
//
//    @PatchMapping
}
