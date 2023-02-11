package ru.practicum.ewm.event;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventResponseDto;
import ru.practicum.ewm.event.dto.EventResponseDtoShort;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.ewm.category.CatMapper.toCatDto;
import static ru.practicum.ewm.location.LocationMapper.toLocationRequestDto;
import static ru.practicum.ewm.user.UserMapper.toUserResponseDto;

@Component
public class EventMapper {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Event toEvent(EventRequestDto eventRequestDto, Cat eventCat, Location eventLocation,
                                User eventInitiator) {

        return new Event(
                eventRequestDto.getTitle(),
                eventRequestDto.getAnnotation(),
                eventRequestDto.getDescription(),
                eventInitiator,
                LocalDateTime.now(),
                eventCat,
                LocalDateTime.parse(eventRequestDto.getEventDate(), dateTimeFormatter),
                eventRequestDto.getPaid(),
                eventRequestDto.getParticipantLimit(),
                eventRequestDto.getRequestModeration(),
                EventState.PENDING.toString(),
                0L,
                eventLocation
        );
    }

    public static EventResponseDto toEventResponseDto(Event event) {

        String eventPublishedOn = null;
        if (event.getEventPublishedOn() != null) {
            eventPublishedOn = dateTimeFormatter.format(event.getEventPublishedOn());
        }

        return new EventResponseDto(
                event.getEventAnnotation(),
                toCatDto(event.getEventCat()),
                event.getEventConfirmedRequests(),
                dateTimeFormatter.format(event.getEventCreated()),
                event.getEventDescription(),
                dateTimeFormatter.format(event.getEventDate()),
                event.getId(),
                toUserResponseDto(event.getEventInitiator()),
                toLocationRequestDto(event.getEventLocation()),
                event.getEventPaid(),
                event.getEventLimit(),
                eventPublishedOn,
                event.getEventRequestModeration(),
                event.getEventState(),
                event.getEventTitle(),
                event.getEventViews()
        );
    }

    public static EventResponseDtoShort toEventResponseDtoShort(Event event) {
        return new EventResponseDtoShort(
                event.getEventAnnotation(),
                toCatDto(event.getEventCat()),
                event.getEventConfirmedRequests(),
                dateTimeFormatter.format(event.getEventDate()),
                event.getId(),
                toUserResponseDto(event.getEventInitiator()),
                event.getEventPaid(),
                event.getEventTitle(),
                event.getEventViews()
        );
    }
}
