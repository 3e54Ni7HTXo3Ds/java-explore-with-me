package ru.practicum.ewm.category;

import ru.practicum.ewm.category.dto.CatDto;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.error.exceptions.ConflictException;

public interface CatService {
    Cat create(CatDto catDto);

    Cat update(Long id, CatDto catDto) throws ConflictException;

    void delete(Long id);
}
