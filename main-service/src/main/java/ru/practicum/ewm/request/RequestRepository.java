package ru.practicum.ewm.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.request.model.Request;

import java.util.Collection;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Collection<Request> getAllByEventId(Long eventId);

    Boolean existsByRequesterIdAndEventId(Long userId, Long id);
}
