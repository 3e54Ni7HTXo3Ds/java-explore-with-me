package ru.practicum.ewm.compilation.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.CompilationService;
import ru.practicum.ewm.compilation.Dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.Dto.CompilationResponseDto;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;


@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationResponseDto createCompilation(@Valid @RequestBody CompilationRequestDto compilationRequestDto) {
        return compilationService.createCompilation(compilationRequestDto);
    }

    @PatchMapping("/{id}")
    public CompilationResponseDto updateCompilation(@PathVariable Long id,
                                                    @RequestBody CompilationRequestDto compilationRequestDto)
            throws NotFoundParameterException {
        return compilationService.updateCompilation(id, compilationRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@Positive @PathVariable("id") Long id) {
        compilationService.deleteCompilation(id);
    }

}
