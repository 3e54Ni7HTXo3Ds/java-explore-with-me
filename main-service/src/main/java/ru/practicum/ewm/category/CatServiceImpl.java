package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CatDto;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.category.CatMapper.toCat;
import static ru.practicum.ewm.category.CatMapper.toCatDto;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;

    private final EventRepository eventRepository;

    @Override
    public CatDto create(CatDto catDto) throws ConflictException {
        Cat cat = toCat(catDto);

        if (catRepository.existsByName(cat.getName())) throw new ConflictException("Cat name exists");

        return toCatDto(catRepository.save(cat));
    }

    @Override
    public CatDto update(Long id, CatDto catDto) throws ConflictException {
        Cat cat = toCat(catDto);
        if (!catRepository.existsById(id)) {
            throw new ConflictException("Cat not exists");
        }

        if (catRepository.existsByName(cat.getName())) {
            throw new ConflictException("Cat name exists");
        }
        cat.setId(id);
        return toCatDto(catRepository.save(cat));
    }

    @Override
    public void delete(Long id) throws ConflictException {
        Cat cat = catRepository.findById(id).orElseThrow(() -> new ConflictException("Wrong cat"));
        if (eventRepository.existsByEventCat(cat)) throw new ConflictException("Cat is not empty");
        catRepository.deleteById(id);
    }

    @Override
    public List<CatDto> getCatsPageble(PageRequest pageRequest) {
        return catRepository.findAll(pageRequest).stream()
                .map(CatMapper::toCatDto)
                .collect(Collectors.toList());
    }

    @Override
    public CatDto getCatById(Long id) throws NotFoundParameterException {
        return toCatDto(catRepository.findById(id).orElseThrow(() -> new NotFoundParameterException("Wrong cat id")));
    }
}
