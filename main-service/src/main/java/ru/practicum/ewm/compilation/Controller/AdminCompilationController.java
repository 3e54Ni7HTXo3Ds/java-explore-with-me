package ru.practicum.ewm.compilation.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.CompilationService;
import ru.practicum.ewm.compilation.Dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.Dto.CompilationResponseDto;

import javax.validation.constraints.Positive;

import static ru.practicum.ewm.compilation.CompilationMapper.toCompilationResponseDto;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationResponseDto createCompilation(@RequestBody CompilationRequestDto compilationRequestDto) {
        return toCompilationResponseDto(compilationService.createCompilation(compilationRequestDto));
    }

    @PatchMapping("/{id}")
    public CompilationResponseDto updateCompilation(@PathVariable Long id,
                                                    @RequestBody CompilationRequestDto compilationRequestDto) {
        return toCompilationResponseDto(compilationService.updateCompilation(id, compilationRequestDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@Positive @PathVariable("id") Long id) {
        compilationService.deleteCompilation(id);
    }

}
