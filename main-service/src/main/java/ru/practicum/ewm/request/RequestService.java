package ru.practicum.ewm.request;

import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto createRequest(Long userId, Long eventId) throws NotFoundParameterException, ConflictException;

    List<RequestDto> getRequests(Long userId) throws ConflictException;

    RequestDto cancelRequest(Long userId, Long requestId) throws ConflictException;
}
