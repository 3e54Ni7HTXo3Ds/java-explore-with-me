package ru.practicum.ewm.request;

import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.request.model.Request;

import java.util.List;

public interface RequestService {
    Request createRequest(Long userId, Long eventId) throws NotFoundParameterException, ConflictException;

    List<Request> getRequests(Long userId);

    Request cancelRequest(Long userId, Long requestId);
}
