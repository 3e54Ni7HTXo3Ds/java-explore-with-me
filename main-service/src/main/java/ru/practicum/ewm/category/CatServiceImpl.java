package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CatDto;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.error.exceptions.ConflictException;

import static ru.practicum.ewm.category.CatMapper.toCat;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;

    @Override
    public Cat create(CatDto catDto) {
        Cat cat = toCat(catDto);
        return catRepository.save(cat);
    }

    @Override
    public Cat update(Long id, CatDto catDto) throws ConflictException {
        Cat cat = toCat(catDto);
        if (!catRepository.existsById(id)) {
            throw new ConflictException("Nonexistent");
        }

        if (!catRepository.existsByName(cat.getName())) {
            throw new ConflictException("Name exists");
        }
        cat.setId(id);
        return catRepository.save(cat);
    }

    @Override
    public void delete(Long id) {
        catRepository.deleteById(id);
    }
}
