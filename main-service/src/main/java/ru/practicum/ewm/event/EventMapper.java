package ru.practicum.ewm.event;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.model.Event;

@Component
public class EventMapper {

    public static Event toEvent(EventRequestDto eventRequestDto) {


        return new Event(
        );
    }
}
