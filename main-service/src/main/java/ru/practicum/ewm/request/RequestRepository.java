package ru.practicum.ewm.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.request.model.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> getAllByEventIdAndStatus(Long eventId, String status);

    Boolean existsByRequesterIdAndEventId(Long userId, Long id);

    List<Request> findAllByRequesterId(Long userId);

    List<Request> findAllByInitiatorId(Long userId);

    List<Request> findAllByIdInAndStatus(List<Long> id, String status);
}
