package ru.practicum.ewm.compilation.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.CompilationService;
import ru.practicum.ewm.compilation.Dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.Dto.CompilationResponseDto;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.request.RequestMapper;
import ru.practicum.ewm.request.dto.RequestDto;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.compilation.CompilationMapper.toCompilationResponseDto;
import static ru.practicum.ewm.request.RequestMapper.toRequestDto;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationResponseDto createCompilation(@RequestBody CompilationRequestDto compilationRequestDto
                                       ) throws NotFoundParameterException, ConflictException {
        return toCompilationResponseDto(compilationService.createCompilation(compilationRequestDto));
    }


    @GetMapping
    public List<RequestDto> getRequests(@PathVariable Long userId) throws ConflictException {
        return requestService.getRequests(userId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long userId,
                                    @PathVariable Long requestId) throws ConflictException {
        return toRequestDto(requestService.cancelRequest(userId, requestId));
    }

}
