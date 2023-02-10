package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CatDto;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

import static ru.practicum.ewm.category.CatMapper.toCat;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;

    @Override
    public Cat create(CatDto catDto) throws ConflictException {
        Cat cat = toCat(catDto);

        if (catRepository.existsByName(cat.getName())) throw new ConflictException("Cat name exists");

        return catRepository.save(cat);
    }

    @Override
    public Cat update(Long id, CatDto catDto) throws ConflictException {
        Cat cat = toCat(catDto);
        if (!catRepository.existsById(id)) {
            throw new ConflictException("Cat not exists");
        }

        if (catRepository.existsByName(cat.getName())) {
            throw new ConflictException("Cat name exists");
        }
        cat.setId(id);
        return catRepository.save(cat);
    }

    @Override
    public void delete(Long id) {
        catRepository.deleteById(id);
    }

    @Override
    public Page<Cat> getCatsPageble(PageRequest pageRequest) {
        return catRepository.findAll(pageRequest);
    }

    @Override
    public Cat getCatById(Long id) throws NotFoundParameterException {
        return catRepository.findById(id).orElseThrow(new NotFoundParameterException("Wrong cat id"));
    }
}
