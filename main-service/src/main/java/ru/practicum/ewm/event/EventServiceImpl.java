package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.CatRepository;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.IncorrectParameterException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventRequestDtoUpdate;
import ru.practicum.ewm.event.dto.EventResponseDto;
import ru.practicum.ewm.event.dto.EventResponseDtoShort;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.location.LocationRepository;
import ru.practicum.ewm.location.dto.LocationRequestDto;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.request.RequestMapper;
import ru.practicum.ewm.request.RequestRepository;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.dto.RequestDtoShort;
import ru.practicum.ewm.request.dto.RequestResponseDtoShort;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestState;
import ru.practicum.ewm.stats.dto.HitResponseDto;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.model.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ofPattern;
import static ru.practicum.ewm.event.EventMapper.toEventResponseDto;
import static ru.practicum.ewm.location.LocationMapper.toLocation;
import static ru.practicum.ewm.request.RequestMapper.toRequestDto;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CatRepository catRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private static final DateTimeFormatter dateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public EventResponseDto createEvent(Long userId, EventRequestDto eventRequestDto)
            throws NotFoundParameterException, ConflictException {

        if (!(LocalDateTime.parse(eventRequestDto.getEventDate(), dateTimeFormatter)
                .isAfter(LocalDateTime.now().plusHours(2))))
            throw new ConflictException("Event must be at least 2 ours later");

        Cat eventCat = catRepository.findById(eventRequestDto.getCategory())
                .orElseThrow(new NotFoundParameterException("Wrong cat"));

        Location eventLocation = locationRepository.save(toLocation(eventRequestDto.getLocation()));

        User eventInitiator = userRepository.findById(userId)
                .orElseThrow(new NotFoundParameterException("Wrong user"));

        Event event = EventMapper.toEvent(eventRequestDto, eventCat, eventLocation, eventInitiator);
        return toEventResponseDto(eventRepository.save(event));
    }

    @Override
    public List<EventResponseDtoShort> getEvents(Long userId, int from, int size) {
        OffsetBasedPageRequest offsetBasedPageRequest =
                new OffsetBasedPageRequest(from, size, Sort.by("id"));

        return eventRepository.findAllByEventInitiatorId(userId, offsetBasedPageRequest).stream()
                .map(EventMapper::toEventResponseDtoShort)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDto getEvent(Long userId, Long eventId, List<HitResponseDto> hitResponseDtos, String uri)
            throws NotFoundParameterException {

        Event event = eventRepository.findById(eventId).orElseThrow(new NotFoundParameterException("Wrong event"));

        event.setEventConfirmedRequests(
                requestRepository.countAllByEventIdAndStatus(eventId, RequestState.CONFIRMED.toString()));
        if (hitResponseDtos != null && !hitResponseDtos.isEmpty())
            for (HitResponseDto dto : hitResponseDtos) {
                if (dto.getUri().equals(uri))
                    event.setEventViews(dto.getHits());
            }
        return toEventResponseDto(eventRepository.save(event));
    }

    @Override
    public EventResponseDto updateEvent(Long userId, Long eventId, EventRequestDtoUpdate eventRequestDtoUpdate, Boolean admin)
            throws IncorrectParameterException, ConflictException {

        String annotation = eventRequestDtoUpdate.getAnnotation();
        Long category = eventRequestDtoUpdate.getCategory();
        String description = eventRequestDtoUpdate.getDescription();
        String eventDate = eventRequestDtoUpdate.getEventDate();
        LocationRequestDto location = eventRequestDtoUpdate.getLocation();
        Boolean paid = eventRequestDtoUpdate.getPaid();
        Long participantLimit = eventRequestDtoUpdate.getParticipantLimit();
        Boolean requestModeration = eventRequestDtoUpdate.getRequestModeration();
        String stateAction = eventRequestDtoUpdate.getStateAction();
        String title = eventRequestDtoUpdate.getTitle();


        Event event = eventRepository.findById(eventId)
                .orElseThrow(new IncorrectParameterException("Event with id=" + eventId + " was not found"));

        if (!admin && userId != null) {
            if (!userId.equals(event.getEventInitiator().getId()) ||
                    event.getEventState().equals(String.valueOf(EventState.PUBLISHED)))
                throw new ConflictException("Wrong user or published event");

            userRepository.findById(userId)
                    .orElseThrow(new IncorrectParameterException("User with id=" + userId + " was not found"));
        }

        if (stateAction != null) {
            switch (EventState.valueOf(eventRequestDtoUpdate.getStateAction())) {
                case PUBLISH_EVENT:
                    if (admin && event.getEventState().equals(String.valueOf(EventState.PENDING))) {
                        event.setEventState(String.valueOf(EventState.PUBLISHED));
                        event.setEventPublishedOn(LocalDateTime.now());
                    } else
                        throw new ConflictException("Cannot publish the event because it's not in the right state");
                    break;
                case CANCEL_REVIEW:
                    if (!event.getEventState().equals(String.valueOf(EventState.PUBLISHED))) {
                        event.setEventState(String.valueOf(EventState.CANCELED));
                    } else
                        throw new ConflictException("Cannot cancel the event because it's not in the right state");
                    break;
                case REJECT_EVENT:
                    if (admin && !event.getEventState().equals(String.valueOf(EventState.PUBLISHED))) {
                        event.setEventState(String.valueOf(EventState.CANCELED));
                    } else
                        throw new ConflictException("Cannot reject the event because it's not in the right state");
                    break;
                case SEND_TO_REVIEW:
                    event.setEventState(String.valueOf(EventState.PENDING));
                    break;
            }
        }

        if (eventDate != null) {
            LocalDateTime eventDateTime = LocalDateTime.parse(eventDate, dateTimeFormatter);

            if (admin && !(eventDateTime).isAfter(LocalDateTime.now().plusHours(1)))
                throw new ConflictException("Event must be at least 1 ours later");

            if (!admin && !(eventDateTime).isAfter(LocalDateTime.now().plusHours(2)))
                throw new ConflictException("Event must be at least 2 ours later");

            event.setEventDate(eventDateTime);
        }
        if (category != null) {
            Cat cat = catRepository.findById(eventRequestDtoUpdate.getCategory())
                    .orElseThrow(() -> new IncorrectParameterException("Cat was not found"));
            event.setEventCat(cat);
        }
        if (location != null) {
            Location newEventLocation = toLocation(eventRequestDtoUpdate.getLocation());
            if (!event.getEventLocation().equals(newEventLocation)) {
                locationRepository.save(newEventLocation);
            }
            event.setEventLocation(newEventLocation);
        }

        if (annotation != null) event.setEventAnnotation(annotation);
        if (description != null) event.setEventDescription(description);
        if (title != null) event.setEventTitle(title);
        if (paid != null) event.setEventPaid(paid);
        if (participantLimit != null && participantLimit >= 0) event.setEventLimit(participantLimit);
        if (requestModeration != null) event.setEventRequestModeration(requestModeration);

        return toEventResponseDto(eventRepository.save(event));
    }

    @Override
    public List<EventResponseDto> getEventsAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                      String rangeEnd, int from, int size) {
        OffsetBasedPageRequest offsetBasedPageRequest =
                new OffsetBasedPageRequest(from, size, Sort.by("id"));

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        if (rangeStart != null) startTime = LocalDateTime.parse(rangeStart, dateTimeFormatter);
        if (rangeEnd != null) endTime = LocalDateTime.parse(rangeEnd, dateTimeFormatter);

        return eventRepository.findEventsAdmin(users, states, categories, startTime, endTime, offsetBasedPageRequest).stream()
                .map(EventMapper::toEventResponseDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<EventResponseDto> getEventsPublic(String text, List<Long> categories, Boolean paid, String rangeStart,
                                       String rangeEnd, Boolean onlyAvailable, String sort, int from, int size,
                                       List<HitResponseDto> hitResponseDtos,
                                       HttpServletRequest httpServletRequest) {

        if ("VIEWS".equals(sort)) {
            sort = "eventViews";
        } else {
            sort = "eventDate";
        }

        OffsetBasedPageRequest offsetBasedPageRequest =
                new OffsetBasedPageRequest(from, size, Sort.by(sort).ascending());

        LocalDateTime startTime;
        LocalDateTime endTime;

        if (rangeStart != null) startTime = LocalDateTime.parse(rangeStart, dateTimeFormatter);
        else startTime = LocalDateTime.now();
        if (rangeEnd != null) endTime = LocalDateTime.parse(rangeEnd, dateTimeFormatter);
        else endTime = LocalDateTime.now().plusYears(100);
        if (text != null) text = text.toLowerCase();

        List<Event> list = eventRepository.findEventsPublic(text, categories, paid, startTime, endTime,
                onlyAvailable, offsetBasedPageRequest);

        if (onlyAvailable) {
            list.removeIf(event -> event.getEventConfirmedRequests() >= event.getEventLimit());
        }

        return list.stream()
                .map(EventMapper::toEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> getEventRequests(Long userId, Long eventId) throws ConflictException {
        userRepository.findById(userId).orElseThrow(new ConflictException("Wrong user"));
        eventRepository.findById(eventId).orElseThrow(new ConflictException("Wrong event"));
        return requestRepository.findAllByInitiatorId(userId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestResponseDtoShort updateEventRequests(Long userId, Long eventId, RequestDtoShort requestDtoShort)
            throws ConflictException {
        userRepository.findById(userId).orElseThrow(new ConflictException("Wrong user"));
        Event event = eventRepository.findById(eventId).orElseThrow(new ConflictException("Wrong event"));
        Long eventLimit = event.getEventLimit();
        Long confirmed = event.getEventConfirmedRequests();

        if (Objects.equals(eventLimit, confirmed)) throw new ConflictException("No place");

        List<RequestDto> confirmedRequests = new ArrayList<>();
        List<RequestDto> rejectedRequests = new ArrayList<>();
        RequestResponseDtoShort requestResponseDtoShort =
                new RequestResponseDtoShort(confirmedRequests, rejectedRequests);
        List<Long> ids = requestDtoShort.getRequestIds();
        String status = requestDtoShort.getStatus();

        List<Request> allInitiatorPendingRequests =
                requestRepository.findAllByIdInAndStatus(ids, RequestState.PENDING.toString());

        if (ids.size() != allInitiatorPendingRequests.size()) throw new ConflictException("Only PENDING");

        if (allInitiatorPendingRequests.isEmpty() || (!event.getEventRequestModeration() || eventLimit == 0)) {
            return requestResponseDtoShort;
        }

        if (status.equals(RequestState.CONFIRMED.toString())) {
            for (Request request : allInitiatorPendingRequests) {
                request.setStatus(RequestState.CONFIRMED.toString());
                confirmedRequests.add(toRequestDto(request));
                confirmed++;
                if (eventLimit.equals(confirmed)) {
                    for (int i = allInitiatorPendingRequests.indexOf(request);
                         i < allInitiatorPendingRequests.size() - 1; i++) {
                        request.setStatus(RequestState.REJECTED.toString());
                        rejectedRequests.add(toRequestDto(request));
                    }
                    break;
                }
            }
            requestRepository.saveAll(allInitiatorPendingRequests);
        }

        if (status.equals(RequestState.REJECTED.toString())) {
            for (Request request : allInitiatorPendingRequests) {
                request.setStatus(RequestState.REJECTED.toString());
                rejectedRequests.add(toRequestDto(request));
            }
            requestRepository.saveAll(allInitiatorPendingRequests);
        }
        event.setEventConfirmedRequests(confirmed);
        eventRepository.save(event);

        return requestResponseDtoShort;
    }
}

