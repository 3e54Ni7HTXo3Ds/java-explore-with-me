package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.CatRepository;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.dto.EventResponseDto;
import ru.practicum.ewm.location.LocationRepository;
import ru.practicum.ewm.user.UserRepository;

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
    public EventResponseDto createEvent(Long userId, EventRequestDto eventRequestDto) {

//        Cat cat = catRepository.findById(eventRequestDto.get
//                Event event = EventMapper.toEvent(eventRequestDto);
        return null;
    }
}
