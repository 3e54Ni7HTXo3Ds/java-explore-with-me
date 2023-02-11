package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.CatRepository;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.error.exceptions.IncorrectParameterException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventRequestDtoUpdate;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.location.LocationRepository;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;
import static ru.practicum.ewm.location.LocationMapper.toLocation;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CatRepository catRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    private static final DateTimeFormatter dateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Event createEvent(Long userId, EventRequestDto eventRequestDto)
            throws NotFoundParameterException, IncorrectParameterException {

        if (!(LocalDateTime.parse(eventRequestDto.getEventDate(), dateTimeFormatter)
                .isAfter(LocalDateTime.now().plusHours(2))))
            throw new IncorrectParameterException("Event must be at least 2 ours later");

        Cat eventCat = catRepository.findById(eventRequestDto.getCategory())
                .orElseThrow(new NotFoundParameterException("Wrong cat"));

        Location eventLocation = locationRepository.save(toLocation(eventRequestDto.getLocation()));

        User eventInitiator = userRepository.findById(userId)
                .orElseThrow(new NotFoundParameterException("Wrong user"));

        Event event = EventMapper.toEvent(eventRequestDto, eventCat, eventLocation, eventInitiator);
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getEvents(Long userId, int from, int size) {
        OffsetBasedPageRequest offsetBasedPageRequest =
                new OffsetBasedPageRequest(from, size, Sort.by("id"));

        return eventRepository.findAllByEventInitiatorId(userId, offsetBasedPageRequest);
    }

    @Override
    public Event getEvent(Long userId, Long eventId) throws IncorrectParameterException {
        return eventRepository.findById(eventId).orElseThrow(new IncorrectParameterException("Wrong event"));
    }

    @Override
    public Event updateEvent(Long userId, Long eventId, EventRequestDtoUpdate eventRequestDtoUpdate)
            throws IncorrectParameterException {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(new IncorrectParameterException("Event with id=" + eventId + " was not found"));

//        userRepository.findById(userId)
//                .orElseThrow(new IncorrectParameterException("User with id=" + userId + " was not found"));
//        Cat cat = catRepository.findById(eventRequestDtoUpdate.getCategory())
//                .orElseThrow(() -> new IncorrectParameterException("Cat was not found"));
//
//        LocalDateTime eventDate = LocalDateTime.parse(eventRequestDtoUpdate.getEventDate(), dateTimeFormatter);
//        Location newEventLocation = toLocation(eventRequestDtoUpdate.getLocation());
//
//        if (!(eventDate).isAfter(LocalDateTime.now().plusHours(2)))
//            throw new IncorrectParameterException("Event must be at least 2 ours later");
//
//        if (!Objects.equals(event.getEventInitiator().getId(), userId))
//            throw new IncorrectParameterException("Wrong user");
//
//        if (event.getEventState().equals("PUBLISHED")) throw new UpdateException("Event must not be published");
//
//        if (!event.getEventLocation().equals(newEventLocation)) {
//            locationRepository.save(newEventLocation);
//        }

//        event.setEventAnnotation(eventRequestDtoUpdate.getAnnotation());
//        event.setEventCat(cat);
//        event.setEventDescription(eventRequestDtoUpdate.getDescription());
//        event.setEventDate(eventDate);
//        event.setEventLocation(newEventLocation);
//        event.setEventPaid(eventRequestDtoUpdate.getPaid());
//        event.setEventLimit(eventRequestDtoUpdate.getParticipantLimit());
//        event.setEventRequestModeration(eventRequestDtoUpdate.getRequestModeration());
        //   event.setEventTitle(eventRequestDtoUpdate.getTitle());

        switch (EventState.valueOf(eventRequestDtoUpdate.getStateAction())) {
            case CANCEL_REVIEW:
                event.setEventState(String.valueOf(EventState.CANCELED));
                break;
            case SEND_TO_REVIEW:
                event.setEventState(String.valueOf(EventState.PENDING));
                break;
        }

        return eventRepository.save(event);
    }

}
