package ru.practicum.ewm.compilation;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.Dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.Dto.CompilationResponseDto;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

import java.util.List;

@Service
public interface CompilationService {
    CompilationResponseDto createCompilation(CompilationRequestDto compilationRequestDto);

    CompilationResponseDto updateCompilation(Long id, CompilationRequestDto compilationRequestDto)
            throws NotFoundParameterException;

    void deleteCompilation(Long id);

    List<CompilationResponseDto> getCompilations(Boolean pinned, int from, int size);

    CompilationResponseDto getCompilation(Long id) throws NotFoundParameterException;
}
