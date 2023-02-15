package ru.practicum.ewm.compilation.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.CompilationService;
import ru.practicum.ewm.compilation.Dto.CompilationResponseDto;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationResponseDto> getCompilations(
            @RequestParam(required = false, defaultValue = "false") Boolean pinned,
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
            @Positive @RequestParam(required = false, defaultValue = "10") int size) {
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping(path = "/{id}")
    public CompilationResponseDto getCompilation(@PathVariable Long id)
            throws NotFoundParameterException {
        return compilationService.getCompilation(id);
    }

}
