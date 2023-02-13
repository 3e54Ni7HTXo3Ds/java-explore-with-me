package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.Dto.CompilationRequestDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.event.OffsetBasedPageRequest;

import java.util.List;

import static ru.practicum.ewm.compilation.CompilationMapper.toCompilation;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public Compilation createCompilation(CompilationRequestDto compilationRequestDto) {

        Compilation compilation = toCompilation(compilationRequestDto);
        compilation.setEventList(eventRepository.findAllById(compilationRequestDto.getEvents()));
        return compilationRepository.save(compilation);
    }

    @Override
    public Compilation updateCompilation(Long id, CompilationRequestDto compilationRequestDto)
            throws NotFoundParameterException {

        Compilation compilation =
                compilationRepository.findById(id).orElseThrow(new NotFoundParameterException("Wrong compilation"));
        compilation.setEventList(eventRepository.findAllById(compilationRequestDto.getEvents()));
        compilation.setTitle(compilationRequestDto.getTitle());
        compilation.setPinned(compilationRequestDto.getPinned());

        return compilationRepository.save(compilation);
    }

    @Override
    public void deleteCompilation(Long id) {
        compilationRepository.deleteById(id);
    }

    @Override
    public List<Compilation> getCompilations(Boolean pinned, int from, int size) {
        OffsetBasedPageRequest offsetBasedPageRequest = new OffsetBasedPageRequest(from, size);
        return compilationRepository.findAllByPinned(pinned, offsetBasedPageRequest);
    }

    @Override
    public Compilation getCompilation(Long id) throws NotFoundParameterException {
        return compilationRepository.findById(id).orElseThrow(new NotFoundParameterException("Not found"));
    }
}
