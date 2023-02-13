package ru.practicum.ewm.compilation;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.compilation.Dto.CompilationResponseDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.model.Request;

@Component
public class CompilationMapper {

    public static CompilationResponseDto toCompilationResponseDto(Compilation compilation) {
        return new CompilationResponseDto(
                compilation.getEventList(),
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle()
        );
    }
}
