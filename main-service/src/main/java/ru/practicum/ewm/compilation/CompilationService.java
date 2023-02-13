package ru.practicum.ewm.compilation;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.Dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

import java.util.List;

@Service
public interface CompilationService {
    Compilation createCompilation(CompilationRequestDto compilationRequestDto);

    Compilation updateCompilation(Long id, CompilationRequestDto compilationRequestDto)
            throws NotFoundParameterException;

    void deleteCompilation(Long id);

    List<Compilation> getCompilations(Boolean pinned, int from, int size);

    Compilation getCompilation(Long id) throws NotFoundParameterException;
}
