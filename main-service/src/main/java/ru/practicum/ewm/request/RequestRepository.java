package ru.practicum.ewm.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> getAllByEventIdAndStatusIn(Long eventId, List<String> statuses);

    List<Request> getAllByEventInAndStatus(List<Event> eventId, String status);

    Boolean existsByRequesterIdAndEventId(Long userId, Long id);

    List<Request> findAllByRequesterId(Long userId);

    List<Request> findAllByInitiatorId(Long userId);

    Long countAllByEventIdAndStatus(Long id, String status);

}
