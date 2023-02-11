package ru.practicum.ewm.event;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.event.dto.EventRequestDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.user.model.User;

@Component
public class EventMapper {

    public static Event toEvent(EventRequestDto eventRequestDto, Cat cat, Location location, User user) {


        return new Event(
        );
    }
}
