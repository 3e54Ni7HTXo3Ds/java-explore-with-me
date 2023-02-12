package ru.practicum.ewm.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.practicum.ewm.category.dto.CatDto;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

public interface CatService {
    Cat create(CatDto catDto) throws ConflictException;

    Cat update(Long id, CatDto catDto) throws ConflictException;

    void delete(Long id) throws ConflictException;

    Page<Cat> getCatsPageble(PageRequest pageRequest);

    Cat getCatById(Long id) throws NotFoundParameterException;
}
