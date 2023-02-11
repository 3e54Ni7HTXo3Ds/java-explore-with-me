package ru.practicum.ewm.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByEventInitiatorId (Long userId, OffsetBasedPageRequest offsetBasedPageRequest);
}
