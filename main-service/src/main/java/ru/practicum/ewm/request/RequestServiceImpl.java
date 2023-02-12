package ru.practicum.ewm.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestState;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public Request createRequest(Long userId, Long eventId) throws ConflictException {

        Event event = eventRepository.findById(eventId).orElseThrow(new ConflictException("Wrong event"));
        User requester = userRepository.findById(userId).orElseThrow(new ConflictException("Wrong user"));

        if (requester.equals(event.getEventInitiator())) throw new ConflictException("Wrong user");

        if (requestRepository.getAllByEventIdAndStatus(eventId,RequestState.CONFIRMED.toString()).size() >= event.getEventLimit())
            throw new ConflictException("Event is full");

        if (requestRepository.existsByRequesterIdAndEventId(userId, event.getId()))
            throw new ConflictException("Request exists");

        if (event.getEventPublishedOn() == null) throw new ConflictException("Wrong state");

        Request request = new Request();
        request.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        request.setEvent(event);
        request.setRequester(requester);
        request.setInitiator(event.getEventInitiator());

        if (!event.getEventRequestModeration())
            request.setStatus(String.valueOf(RequestState.CONFIRMED));
        else
            request.setStatus(String.valueOf(RequestState.PENDING));

        return requestRepository.save(request);
    }

    @Override
    public List<Request> getRequests(Long userId) throws ConflictException {
        userRepository.findById(userId).orElseThrow(new ConflictException("Wrong user"));
        return requestRepository.findAllByRequesterId(userId);
    }

    @Override
    public Request cancelRequest(Long userId, Long requestId) throws ConflictException {
        userRepository.findById(userId).orElseThrow(new ConflictException("Wrong user"));
        Request request = requestRepository.findById(requestId).orElseThrow(new ConflictException("Wrong request"));
        request.setStatus(String.valueOf(RequestState.CANCELED));
        return requestRepository.save(request);
    }
}
