package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.CatRepository;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.error.exceptions.IncorrectParameterException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.location.LocationRepository;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
}
