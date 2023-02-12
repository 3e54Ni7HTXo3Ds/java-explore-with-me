package ru.practicum.ewm.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.request.dto.RequestDto;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.request.RequestMapper.toRequestDto;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createRequest(@PathVariable Long userId,
                                    @RequestParam Long eventId) throws NotFoundParameterException, ConflictException {
        return toRequestDto(requestService.createRequest(userId, eventId));
    }

    @GetMapping
    public List<RequestDto> getRequests(@PathVariable Long userId) {
        return requestService.getRequests(userId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long userId,
                                    @PathVariable Long requestId) {
        return toRequestDto(requestService.cancelRequest(userId, requestId));
    }
}
