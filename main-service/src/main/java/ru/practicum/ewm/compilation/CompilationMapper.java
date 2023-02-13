package ru.practicum.ewm.compilation;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.compilation.Dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.Dto.CompilationResponseDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.EventMapper;
import ru.practicum.ewm.event.dto.EventResponseDtoShort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompilationMapper {

    public static CompilationResponseDto toCompilationResponseDto(Compilation compilation) {
        List<EventResponseDtoShort> list = new ArrayList<>();
        if (compilation.getEventList() != null) {
            list = compilation.getEventList().stream()
                    .map(EventMapper::toEventResponseDtoShort)
                    .collect(Collectors.toList());
        }
        return new CompilationResponseDto(
                list,
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle()
        );
    }

    public static Compilation toCompilation(CompilationRequestDto compilationRequestDto) {

        return new Compilation(
                null,
                compilationRequestDto.getPinned(),
                compilationRequestDto.getTitle(),
                null);
    }
}
