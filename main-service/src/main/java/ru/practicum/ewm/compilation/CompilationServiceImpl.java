package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.Dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.Dto.CompilationResponseDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.event.OffsetBasedPageRequest;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.compilation.CompilationMapper.toCompilation;
import static ru.practicum.ewm.compilation.CompilationMapper.toCompilationResponseDto;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationResponseDto createCompilation(CompilationRequestDto compilationRequestDto) {

        Compilation compilation = toCompilation(compilationRequestDto);
        compilation.setEventList(eventRepository.findAllById(compilationRequestDto.getEvents()));
        return toCompilationResponseDto(compilationRepository.save(compilation));
    }

    @Override
    public CompilationResponseDto updateCompilation(Long id, CompilationRequestDto compilationRequestDto)
            throws NotFoundParameterException {

        Compilation compilation =
                compilationRepository.findById(id).orElseThrow(() -> new NotFoundParameterException("Wrong compilation"));
        compilation.setEventList(eventRepository.findAllById(compilationRequestDto.getEvents()));
        compilation.setTitle(compilationRequestDto.getTitle());
        compilation.setPinned(compilationRequestDto.getPinned());

        return toCompilationResponseDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Long id) {
        compilationRepository.deleteById(id);
    }

    @Override
    public List<CompilationResponseDto> getCompilations(Boolean pinned, int from, int size) {
        OffsetBasedPageRequest offsetBasedPageRequest = new OffsetBasedPageRequest(from, size);
        return compilationRepository.findAllByPinned(pinned, offsetBasedPageRequest).stream()
                .map(CompilationMapper::toCompilationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationResponseDto getCompilation(Long id) throws NotFoundParameterException {
        return toCompilationResponseDto(
                compilationRepository.findById(id).orElseThrow(() -> new NotFoundParameterException("Not found")));
    }
}
