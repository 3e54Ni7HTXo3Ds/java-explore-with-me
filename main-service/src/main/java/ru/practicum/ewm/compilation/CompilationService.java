package ru.practicum.ewm.compilation;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.Dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.model.Compilation;

@Service
public interface CompilationService {
    Compilation createCompilation(CompilationRequestDto compilationRequestDto);
    }
