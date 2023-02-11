package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.CatRepository;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventResponseDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.location.LocationRepository;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.model.User;

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

    @Override
    public EventResponseDto createEvent(Long userId, EventRequestDto eventRequestDto)
            throws NotFoundParameterException {

        Cat eventCat = catRepository.findById(eventRequestDto.getEventCat().getId())
                .orElseThrow(new NotFoundParameterException("Wrong cat"));

        Location eventLocation = locationRepository.save(toLocation(eventRequestDto.getEventLocation()));

        User eventInitiator = userRepository.findById(eventRequestDto.getEventInitiator().getId())
                .orElseThrow(new NotFoundParameterException("Wrong user"));

        Event event = EventMapper.toEvent(eventRequestDto, eventCat, eventLocation, eventInitiator);
        return null;
    }
}
